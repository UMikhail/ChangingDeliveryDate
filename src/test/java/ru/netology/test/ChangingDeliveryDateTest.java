package ru.netology.test;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ChangingDeliveryDateTest {
    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder=\"Город\"]").setValue(validUser.getCity());
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").sendKeys(firstMeetingDate);
        $("[name=\"name\"]").setValue(validUser.getName());
        $("[name=\"phone\"]").setValue(validUser.getPhone());
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[text()='Запланировать']").click();
        $("[data-test-id=\"success-notification\"]").shouldHave(text("Успешно!\n" + "Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(30));
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").sendKeys(secondMeetingDate);
        $x("//span[text()='Запланировать']").click();
        $("[data-test-id=\"replan-notification\"]").shouldHave(text("Необходимо подтверждение"), Duration.ofSeconds(30));
        $x("//span[text()='Перепланировать']").click();
        $("[data-test-id=\"success-notification\"]").shouldHave(text("Успешно!\n" + "Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(30));
    }
}
