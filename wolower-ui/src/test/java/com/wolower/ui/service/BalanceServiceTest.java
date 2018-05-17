package com.wolower.ui.service;

import com.wolower.persistence.dao.BalanceDao;
import com.wolower.persistence.model.Balance;
import com.wolower.persistence.model.User;
import com.wolower.ui.util.PriceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BalanceServiceTest {
	@Mock
	private BalanceDao balanceDao;

	@InjectMocks
	@Spy
	private BalanceService service;

	@Test
	public void testGetBalanceString_NullBalance(){
		User user = mock(User.class);
		int id = 1;
		doReturn(id).when(user).getId();
		doReturn(null).when(balanceDao).findOneByUserId(anyInt());

		String result = service.getBalanceString(user);

		assertEquals("0", result);
	}

	public String getBalanceString(User user) {
		Balance balance = this.balanceDao.findOneByUserId(user.getId());
		if (balance == null) {
			return "0";
		}
		return String.format("%s %s", PriceUtils.toString(balance.getBalance()), balance.getCurrency());
	}

	@Test
	public void testGetBalance_NullBalance(){
		User user = mock(User.class);
		int id = 1;
		Balance balance = new Balance();
		doReturn(id).when(user).getId();
		doReturn(null).when(balanceDao).findOneByUserId(anyInt());

		Balance result = service.getBalance(user);

		assertEquals(balance.getBalance(), result.getBalance());
		assertEquals(balance.getCurrency(), result.getCurrency());
		assertEquals(balance.getId(), result.getId());
		assertEquals(balance.getUserId(), result.getUserId());
	}

	@Test
	public void testGetBalance(){
		User user = mock(User.class);
		int id = 1;
		Balance balance = new Balance();
		doReturn(id).when(user).getId();
		doReturn(balance).when(balanceDao).findOneByUserId(anyInt());

		Balance result = service.getBalance(user);

		assertEquals(balance, result);
	}

	@Test
	public void testUpdateBalance(){
		User user = mock(User.class);
		Long amount = 9000L;
		Balance balance = mock(Balance.class);
		doReturn(amount).when(balance).getBalance();
		doReturn(balance).when(service).getBalance(user);

		service.updateBalance(user, amount);

		verify(balance).getBalance();
		verify(balanceDao).save(balance);
	}
}
