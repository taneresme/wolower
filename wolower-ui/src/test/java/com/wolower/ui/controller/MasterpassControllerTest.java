package com.wolower.ui.controller;

import com.mastercard.merchant.checkout.model.Pairing;
import com.mastercard.merchant.checkout.model.PreCheckoutData;
import com.wolower.persistence.model.User;
import com.wolower.ui.model.request.SavePrecheckoutRequest;
import com.wolower.ui.model.responses.BaseResponse;
import com.wolower.ui.service.MasterpassInitializationService;
import com.wolower.ui.service.SessionService;
import com.wolower.ui.util.ResponseCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MasterpassControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterpassController.class);

	@Mock
	private MasterpassInitializationService masterpassInitializationService;

	@Mock
	private SessionService sessionService;

	@InjectMocks
	private MasterpassController controller;

	@Test
	public void testCallback_Cancel(){
		Model model = mock(Model.class);
		String result = controller.callback(model, null, null,
				null, "cancel", null);

		assertEquals("redirect:/profile?tab=payment-method", result);
	}

	@Test
	public void testCallback(){
		Model model = mock(Model.class);
		String oauthToken = "oauthToken";
		String pairingVerifier = "pairingVerifier";
		String pairingToken = "pairingToken";
		String mpstatus = "not-canceled";
		Pairing pairing = mock(Pairing.class);
		doReturn(pairing).when(masterpassInitializationService).obtainAPairingId(pairingVerifier);

		String result = controller.callback(model, oauthToken, pairingVerifier,
				pairingToken, mpstatus, null);

		assertEquals("redirect:/profile?tab=payment-method", result);
		verify(masterpassInitializationService).obtainAPairingId(pairingVerifier);
		verify(masterpassInitializationService).initMasterpass(pairing, oauthToken, pairingVerifier,
				pairingToken, mpstatus);
	}

	@Test
	public void testPrecheckout(){
		PreCheckoutData preCheckoutData = mock(PreCheckoutData.class);
		doReturn(preCheckoutData).when(masterpassInitializationService).getPrecheckoutData();

		ResponseEntity<PreCheckoutData> result = controller.precheckout(null);

		assertEquals(preCheckoutData, result.getBody());
	}

	@Test(expected = Exception.class)
	public void testPrecheckout_Exception(){
		doThrow(new Exception("EXCEPTION")).when(masterpassInitializationService).getPrecheckoutData();

		ResponseEntity<PreCheckoutData> result = controller.precheckout(null);

		assertEquals(null, result.getBody());
	}

	@Test
	public void testSavePrecheckout(){
		BaseResponse baseResponse = new BaseResponse();
		SavePrecheckoutRequest savePrecheckoutRequest = mock(SavePrecheckoutRequest.class);

		ResponseEntity<BaseResponse> result = controller.savePrecheckout(savePrecheckoutRequest, null);

		assertEquals(baseResponse.getResponseCode(), result.getBody().getResponseCode());
		assertEquals(baseResponse.getResponseDesc(), result.getBody().getResponseDesc());
		verify(masterpassInitializationService).saveCardAndAddress(savePrecheckoutRequest);
	}

	@Test(expected = Exception.class)
	public void testSavePrecheckout_Exception(){
		SavePrecheckoutRequest savePrecheckoutRequest = mock(SavePrecheckoutRequest.class);
		doThrow(new Exception("EXCEPTION")).when(masterpassInitializationService).saveCardAndAddress(savePrecheckoutRequest);

		ResponseEntity<BaseResponse> result = controller.savePrecheckout(savePrecheckoutRequest, null);

		assertEquals(ResponseCodes.PRECHECKOUT_SAVING_FAILED, result.getBody().getResponseCode());
	}

	@Test()
	public void testRemovePairing(){
		String socialUserName = "SOCIAL-USER-NAME";
		User user = mock(User.class);
		doReturn(user).when(sessionService).user();
		doReturn(socialUserName).when(user).getSocialUserName();
		String result = controller.removePairing(null);

		assertEquals("redirect:/profile?tab=payment-method", result);
		verify(masterpassInitializationService).removePairing();
	}
}
