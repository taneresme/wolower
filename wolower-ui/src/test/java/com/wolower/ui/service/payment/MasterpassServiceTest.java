package com.wolower.ui.service.payment;

import com.mastercard.merchant.checkout.model.PaymentData;
import com.mastercard.merchant.checkout.model.PreCheckoutData;
import com.wolower.persistence.dao.MasterpassDao;
import com.wolower.persistence.dao.MasterpassExpressCheckoutDao;
import com.wolower.persistence.dao.MasterpassPairingIdDao;
import com.wolower.persistence.dao.MasterpassPrecheckoutDao;
import com.wolower.persistence.enums.PairingIdSources;
import com.wolower.persistence.model.*;
import com.wolower.ui.config.MasterpassConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MasterpassServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterpassService.class);

	@Mock
	private MasterpassConfig config;
	@Mock
	private MasterpassDao masterpassDao;
	@Mock
	private MasterpassPairingIdDao masterpassPairingIdDao;
	@Mock
	private MasterpassPrecheckoutDao masterpassPrecheckoutDao;
	@Mock
	private MasterpassExpressCheckoutDao masterpassExpressCheckoutDao;
	@InjectMocks
	@Spy
	private MasterpassService service;

	@Test
	public void testSavePairingId(){
		User user = mock(User.class);
		int id = 2;
		String pairingId = "pairingId";
		Masterpass masterpass = mock(Masterpass.class);
		PairingIdSources pairingIdSources = PairingIdSources.PRECHECKOUT;

		doNothing().when(service).wasteAllPairingIds(any());
		doReturn(id).when(user).getId();
		doReturn(id).when(masterpass).getId();

		service.savePairingId(pairingId, pairingIdSources, user, masterpass);

		verify(masterpassPairingIdDao, times(1)).save(any(MasterpassPairingId.class));
	}

	@Test
	public void testWasteAllPairingIds(){
		User user = mock(User.class);
		int id = 2;

		doReturn(id).when(user).getId();

		service.wasteAllPairingIds(user);

		verify(masterpassPairingIdDao).wasteThemAllByUserId(anyInt(), anyBoolean());
	}

	@Test
	public void testSavePrecheckout(){
		User user = mock(User.class);
		int id = 2;
		Masterpass masterpass = mock(Masterpass.class);
		PreCheckoutData preCheckoutData = mock(PreCheckoutData.class);
		String consumerWalletId = "consumerWalletId";
		String preCheckoutTransactionId = "preCheckoutTransactionId";
		String walletName = "walletName";

		doReturn(consumerWalletId).when(preCheckoutData).getConsumerWalletId();
		doReturn(id).when(masterpass).getId();
		doReturn(preCheckoutTransactionId).when(preCheckoutData).getPreCheckoutTransactionId();
		doReturn(id).when(user).getId();
		doReturn(walletName).when(preCheckoutData).getWalletName();

		service.savePrecheckout(user, preCheckoutData, masterpass);

		verify(masterpassPrecheckoutDao, times(1)).save(any(MasterpassPrecheckout.class));
	}

	@Test
	public void testGetMasterpass(){
		User user = mock(User.class);
		Masterpass masterpass = mock(Masterpass.class);
		doReturn(masterpass).when(masterpassDao).findOneByUserIdAndEnabled(anyInt(), anyBoolean());

		Masterpass result = service.getMasterpass(user);

		assertEquals(masterpass, result);
	}

	@Test
	public void testGetPairingId(){
		User user = mock(User.class);
		MasterpassPairingId masterpassPairingId = mock(MasterpassPairingId.class);
		String pairingId = "pairingId";

		doReturn(pairingId).when(masterpassPairingId).getPairingId();
		doReturn(masterpassPairingId).when(masterpassPairingIdDao).findOneByUserIdAndWastedOrderByIdDesc(anyInt(), anyBoolean());

		String result = service.getPairingId(user);

		assertEquals(pairingId, result);
	}

	@Test
	public void testSaveExpressCheckout(){
		User user = mock(User.class);
		PaymentData paymentData = mock(PaymentData.class);
		Masterpass masterpass = mock(Masterpass.class);
		MasterpassPairingId masterpassPairingId = mock(MasterpassPairingId.class);
		String pairingId = "pairingId";
		int id = 1;
		String walletId = "walletId";
		String walletName = "walletName";

		doReturn(id).when(user).getId();
		doReturn(id).when(masterpass).getId();
		doReturn(walletId).when(paymentData).getWalletId();
		doReturn(walletName).when(paymentData).getWalletName();

		service.saveExpressCheckout(user, paymentData, masterpass);

		verify(masterpassExpressCheckoutDao, times(1)).save(any(MasterpassExpressCheckout.class));
	}
}
