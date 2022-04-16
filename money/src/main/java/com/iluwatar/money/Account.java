package com.iluwatar.money;

import com.iluwatar.money.exception.BalanceDoesNotExistForAccountException;
import com.iluwatar.money.exception.CurrencyCannotBeExchangedException;
import com.iluwatar.money.exception.CurrencyMismatchException;
import com.iluwatar.money.exception.SubtractionCannotOccurException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a User Account, which holds all the {@link Money}s
 * that this user has.
 */
public class Account { //NOPMD - suppressed DataClass - adapt to the design philosophy of this class

  /**
   * The ID of an {@link Account}.
   */
  private final int accountId;

  /**
   * The primary {@link Currency} of an {@link Account}.
   */
  private Currency primaryCurrency;

  /**
   * The secondary {@link Currency} of an {@link Account}.
   */
  private Currency secondaryCurrency;

  /**
   * The primary Balance ({@link Money}) of an {@link Account}.
   */
  private Money primaryBalance;

  /**
   * The secondary Balance ({@link Money}) of an {@link Account}.
   */
  private Money secondaryBalance;

  /**
   * Constructor.
   *
   * @param accountId  - account ID
   */
  public Account(final int accountId) {
    this.accountId = accountId;
  }

  public void setPrimaryBalance(final Money primaryBalance) {
    this.primaryBalance = primaryBalance;
  }

  public void setSecondaryBalance(final Money secondaryBalance) {
    this.secondaryBalance = secondaryBalance;
  }

  public void setPrimaryCurrency(final Currency primaryCurrency) {
    this.primaryCurrency = primaryCurrency;
  }

  public void setSecondaryCurrency(final Currency secondaryCurrency) {
    this.secondaryCurrency = secondaryCurrency;
  }

  public Currency getPrimaryCurrency() {
    return primaryCurrency;
  }

  public Currency getSecondaryCurrency() {
    return secondaryCurrency;
  }

  public Money getPrimaryBalance() {
    return primaryBalance;
  }

  public Money getSecondaryBalance() {
    return secondaryBalance;
  }

  public int getAccountId() {
    return accountId;
  }

  /**
   * get Account's primary and secondary currency.
   *
   * @return {@link ArrayList}.
   */
  public List<Currency> getCurrencies() {
    return new ArrayList<>(Arrays.asList(this.primaryCurrency, this.secondaryCurrency));
  }

  /**
   * Deposits funds into the corresponding {@link Money} within the {@link Account}.
   *
   * @param moneyToDeposit The {@link Money} object to deposit.
   */
  public void deposit(final Money moneyToDeposit) throws CurrencyMismatchException {
    if (this.primaryCurrency == moneyToDeposit.getCurrency()) {
      if (this.primaryBalance == null) {
        this.primaryBalance = new Money(0, this.primaryCurrency);
      }
      this.primaryBalance = this.primaryBalance.addMoneyBy(moneyToDeposit);
    } else {
      if (this.secondaryBalance == null) {
        this.secondaryBalance = new Money(0, this.secondaryCurrency);
      }
      this.secondaryBalance = this.secondaryBalance.addMoneyBy(moneyToDeposit);
    }
  }

  /**
   * Withdraws funds from an accounts balance.
   *
   * @param moneyToWithdraw The {@link Money} to be withdrawn.
   * @throws SubtractionCannotOccurException If not sufficient funds.
   */
  public void withdraw(final Money moneyToWithdraw) throws BalanceDoesNotExistForAccountException,
      CurrencyCannotBeExchangedException, CurrencyMismatchException,
      SubtractionCannotOccurException {
    validateCurrencyFor(moneyToWithdraw, getCurrencies());
    Money remainedMoney;
    Money totalMoney;
    if (this.primaryCurrency == moneyToWithdraw.getCurrency()) {
      if (this.primaryBalance.getAmount() > moneyToWithdraw.getAmount()) {
        this.primaryBalance = this.primaryBalance.subtractMoneyBy(moneyToWithdraw);
      } else {
        totalMoney = this.primaryBalance.addMoneyBy(CurrencyExchange.convertCurrency(
            this.secondaryBalance,
            ExchangeMethod.assignExchangeMethodBasedOnInput(this.primaryCurrency)));
        remainedMoney = totalMoney.subtractMoneyBy(moneyToWithdraw);
        this.primaryBalance = new Money(0, this.primaryCurrency);
        this.secondaryBalance = CurrencyExchange.convertCurrency(remainedMoney,
            ExchangeMethod.assignExchangeMethodBasedOnInput(this.secondaryCurrency));
      }
    } else {
      if (this.secondaryBalance.getAmount() > moneyToWithdraw.getAmount()) {
        this.secondaryBalance = this.secondaryBalance.subtractMoneyBy(moneyToWithdraw);
      } else {
        totalMoney = this.secondaryBalance.addMoneyBy(CurrencyExchange.convertCurrency(
            this.primaryBalance,
            ExchangeMethod.assignExchangeMethodBasedOnInput(this.secondaryCurrency)));
        remainedMoney = totalMoney.subtractMoneyBy(moneyToWithdraw);
        this.secondaryBalance = new Money(0, this.secondaryCurrency);
        this.primaryBalance = CurrencyExchange.convertCurrency(remainedMoney,
            ExchangeMethod.assignExchangeMethodBasedOnInput(this.primaryCurrency));
      }
    }
  }

  /**
   * Retrieves the {@link Money} corresponding with the {@link Currency}.
   *
   * @param money {@link Money} which is to be retrieved.
   * @throws BalanceDoesNotExistForAccountException If {@link Currency} not exist.
   */
  private void validateCurrencyFor(final Money money, final List<Currency> currencies) throws
      BalanceDoesNotExistForAccountException {
    if (!currencies.contains(money.getCurrency())) {
      throw new BalanceDoesNotExistForAccountException("Currency does not exist");
    }
  }
}
