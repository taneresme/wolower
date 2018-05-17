package com.wolower.ui.service;

import com.wolower.persistence.enums.PairingStatuses;
import com.wolower.persistence.model.Masterpass;
import com.wolower.persistence.model.User;
import com.wolower.ui.model.Profile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {
	private Profile profile = new Profile();
	@Mock
	private SessionService sessionService;
	@Mock
	private MasterpassInitializationService masterpassInitializationService;
	@Mock
	private BalanceService balanceService;
	@InjectMocks
	private ProfileService service;

	@Test
	public void testGetProfile(){
		Profile profile = mock(Profile.class);
		PairingStatuses pairingStatuses = PairingStatuses.NONE;
		Masterpass masterpass = mock(Masterpass.class);
		String checkoutId = "checkoutId";
		String balanceString = "balanceString";
		User user = mock(User.class);
		doReturn(user).when(sessionService).user();
		doReturn(checkoutId).when(masterpassInitializationService).getCheckoutId();
		doReturn(pairingStatuses).when(masterpassInitializationService).masterpassPairingStatus();
		doReturn(masterpass).when(masterpassInitializationService).getMasterpass();
		doReturn(balanceString).when(balanceService).getBalanceString(user);

		service.setProfile(profile);
		Profile result = service.getProfile();

		verify(profile).setUser(user);
		verify(profile).setMasterpassCheckoutId(checkoutId);
		verify(profile).setMasterpassPairingStatus(pairingStatuses);
		verify(profile).setMasterpass(masterpass);
		verify(profile).setBalance(balanceString);
	}
}
