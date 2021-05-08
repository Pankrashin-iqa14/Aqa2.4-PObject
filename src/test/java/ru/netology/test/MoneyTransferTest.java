package ru.netology.test;

import lombok.val;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferFromFirstCard() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstBillBeforeTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondBillBeforeTransfer = dashboardPage.getSecondCardBalance();
        val moneyTransferPage = dashboardPage.secondBill();
        int amount = 5000;
        moneyTransferPage.transferMoney(amount, DataHelper.getCardFirst());
        val balanceFirstBillAfterTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondBillAfterTransfer = dashboardPage.getSecondCardBalance();
        assertEquals((balanceFirstBillBeforeTransfer - amount), balanceFirstBillAfterTransfer);
        assertEquals((balanceSecondBillBeforeTransfer + amount), balanceSecondBillAfterTransfer);
    }


    @Test
    void shouldTransferFromSecondCard() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstBillBeforeTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondBillBeforeTransfer = dashboardPage.getSecondCardBalance();
        val moneyTransferPage = dashboardPage.secondBill();
        int amount = 0;
        moneyTransferPage.transferMoney(amount, DataHelper.getCardFirst());
        val balanceFirstBillAfterTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondBillAfterTransfer = dashboardPage.getSecondCardBalance();
        assertEquals((balanceFirstBillBeforeTransfer - amount), balanceFirstBillAfterTransfer);
        assertEquals((balanceSecondBillBeforeTransfer + amount), balanceSecondBillAfterTransfer);
    }

    @Test
    void shouldTransferMoreAllMoneyFirstCard() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstBillBeforeTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondBillBeforeTransfer = dashboardPage.getSecondCardBalance();
        val moneyTransferPage = dashboardPage.firstBill();
        int amount = 100000;
        moneyTransferPage.transferMoney(amount, DataHelper.getCardSecond());
        moneyTransferPage.errorMessage();
    }
}



