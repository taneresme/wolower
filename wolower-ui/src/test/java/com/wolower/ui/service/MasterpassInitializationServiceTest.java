package com.wolower.ui.service;

import com.mastercard.merchant.checkout.model.Pairing;
import com.mastercard.merchant.checkout.model.PreCheckoutData;
import com.wolower.persistence.dao.MasterpassDao;
import com.wolower.persistence.enums.PairingIdSources;
import com.wolower.persistence.enums.PairingStatuses;
import com.wolower.persistence.model.Masterpass;
import com.wolower.persistence.model.User;
import com.wolower.ui.config.MasterpassConfig;
import com.wolower.ui.model.request.SavePrecheckoutRequest;
import com.wolower.ui.service.payment.MasterpassService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MasterpassInitializationServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterpassInitializationService.class);

	@Mock
	private MasterpassConfig config;
	@Mock
	private MasterpassService masterpassService;
	@Mock
	private SessionService sessionService;
	@Mock
	private MasterpassDao masterpassDao;
//	@Mock
	private Masterpass masterpass;
	@InjectMocks
	@Spy
	private MasterpassInitializationService service;

	@Test
	public void testSetMasterpass(){
		Masterpass masterpass = mock(Masterpass.class);
		service.setMasterpass(masterpass);

		Masterpass result = service.getMasterpass();

		assertEquals(masterpass, result);
	}

	@Test
	public void testGetCheckoutId(){
		String checkoutId = "checkoutId";
		doReturn(checkoutId).when(config).getCheckoutId();

		String result = service.getCheckoutId();

		assertEquals(checkoutId, result);
	}

	@Test
	public void testInitMasterpass(){
		Pairing pairing = mock(Pairing.class);
		String oauthToken = "oauthToken";
		String pairingVerifier = "pairingVerifier";
		String pairingToken = "pairingToken";
		String mpstatus = "mpstatus";
		User user = mock(User.class);
		Masterpass masterpass = mock(Masterpass.class);
		int id = 1;
		doReturn(id).when(user).getId();
		doReturn(user).when(sessionService).user();
		doReturn(masterpass).when(service).getMasterpass();
		doReturn(masterpass).when(masterpassDao).save((Masterpass) anyObject());

		service.initMasterpass(pairing, oauthToken, pairingVerifier, pairingToken, mpstatus);

		verify(masterpass).setUserId(id);
		verify(masterpass).setMpStatus(mpstatus);
		verify(masterpass).setOauthToken(oauthToken);
		verify(masterpass).setPairingToken(pairingToken);
		verify(masterpass).setPairingVerifier(pairingVerifier);
		verify(masterpassService).savePairingId(anyString(), eq(PairingIdSources.INITIALIZATION), eq(user), anyObject());
		verify(masterpassDao, times(2)).save((Masterpass) anyObject());
		verify(masterpass, times(2)).setPairingStatus(any());
	}

	@Test
	public void testGetMasterpass(){
		User user = mock(User.class);
		Masterpass masterpass = mock(Masterpass.class);
		int id = 1;
		doReturn(id).when(user).getId();
		doReturn(user).when(sessionService).user();
		doReturn(masterpass).when(masterpassDao).findOneByUserIdAndEnabled(id, true);

		Masterpass result = service.getMasterpass();

		assertEquals(masterpass, result);
	}

	@Test
	public void testMasterpassPairingStatus(){
		doReturn(null).when(service).getMasterpass();

		PairingStatuses result = service.masterpassPairingStatus();

		assertEquals(PairingStatuses.NONE, result);
	}

	@Test
	public void testGetPrecheckoutData(){
		User user = mock(User.class);
		PreCheckoutData preCheckoutData = mock(PreCheckoutData.class);
		doReturn(user).when(sessionService).user();
		doReturn(preCheckoutData).when(masterpassService).getPrecheckoutData(user);

		PreCheckoutData result = service.getPrecheckoutData();

		assertEquals(preCheckoutData, result);
	}

	@Test
	public void testSaveCardAndAddress(){
		Masterpass masterpass = mock(Masterpass.class);
		SavePrecheckoutRequest savePrecheckoutRequest = mock(SavePrecheckoutRequest.class);
		String recipientName = "recipientName";
		String recipientPhone = "recipientPhone";
		String cardLast4 = "cardLast4";
		String cardId = "cardId";
		String address = "address";
		String addressId = "addressId";
		PairingStatuses pairingStatuses = PairingStatuses.COMPLETED;
		doReturn(recipientName).when(savePrecheckoutRequest).getRecipientName();
		doReturn(recipientPhone).when(savePrecheckoutRequest).getRecipientPhone();
		doReturn(cardLast4).when(savePrecheckoutRequest).getCardLast4();
		doReturn(cardId).when(savePrecheckoutRequest).getCardId();
		doReturn(address).when(savePrecheckoutRequest).getAddress();
		doReturn(addressId).when(savePrecheckoutRequest).getAddressId();

		service.setMasterpass(masterpass);
		service.saveCardAndAddress(savePrecheckoutRequest);

		assertEquals(masterpass, service.getMasterpass());
		verify(masterpass).setRecipientName(recipientName);
		verify(masterpass).setRecipientPhone(recipientPhone);
		verify(masterpass).setDefaultCardLast4(cardLast4);
		verify(masterpass).setDefaultCardId(cardId);
		verify(masterpass).setDefaultShippingAddresss(address);
		verify(masterpass).setDefaultShippingAddressId(addressId);
		verify(masterpass).setPairingStatus(pairingStatuses);
		verify(masterpassDao).save(masterpass);
	}

	@Test
	public void testRemovePairing(){
		User user = mock(User.class);
		int id = 1;
		doReturn(id).when(user).getId();
		doReturn(user).when(sessionService).user();

		service.removePairing();

		assertEquals(null, service.getMasterpass());
		verify(masterpassDao).disableByUserId(id, false);
	}
}
