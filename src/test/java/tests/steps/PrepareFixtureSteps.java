package tests.steps;

import automatization.redmine.context.Context;
import automatization.redmine.cucumber.validators.EmailParametersValidator;
import automatization.redmine.model.user.*;
import automatization.redmine.cucumber.validators.UserParametersValidator;
import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.И;
import io.cucumber.datatable.DataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrepareFixtureSteps {

    @И("Имеется список E-Mail адресов \"(.+)\":")
    public void createEmails(String emailsStashId, DataTable dataTable) {
        EmailParametersValidator.validateEmailParameters(dataTable.row(0));

        List<Map<String, String>> maps = dataTable.asMaps();
        List<Email> emails = new ArrayList<>();

        maps.forEach(map -> {
            String address = map.get("Адрес");
            Boolean isDefault = Boolean.parseBoolean(map.get("По умолчанию"));
            Boolean notify = Boolean.parseBoolean(map.get("Уведомления"));
            Email email = new Email()
                    .setAddress(address)
                    .setIsDefault(isDefault)
                    .setNotify(notify);
            emails.add(email);
        });

        Context.getStash().put(emailsStashId, emails);
    }

    @И("Есть список с \"(.+)\" API токеном\\(ами\\) \"(.+)\"")
    public void createTokens(Integer tokenCount, String tokenStashId) {
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < tokenCount; i++) {
            tokens.add(new Token());
        }
        Context.getStash().put(tokenStashId, tokens);
    }

    @Дано("В системе есть пользователь \"(.+)\" с параметрами:")
    public void createAdminUser(String userStashId, Map<String, String> parameters) {
        UserParametersValidator.validateUserParameters(parameters.keySet());
        User user = new User();
        if (parameters.containsKey("Администратор")) {
            Boolean isAdmin = Boolean.parseBoolean(parameters.get("Администратор"));
            user.setIsAdmin(isAdmin);
        }
        if (parameters.containsKey("Статус")) {
            String statusDescription = parameters.get("Статус");
            Status status = Status.getStatusFromDescription(statusDescription);
            user.setStatus(status);
        }
        if (parameters.containsKey("Уведомления о новых событиях")) {
            String mailNotificationDescription = parameters.get("Уведомления о новых событиях");
            MailNotification mn = MailNotification.getMailNotificationFromDescription(mailNotificationDescription);
            user.setMailNotification(mn);
        }
        if (parameters.containsKey("E-Mail")) {
            String emailsStashId = parameters.get("E-Mail");
            List<Email> emails = Context.getStash().get(emailsStashId, List.class);
            user.setEmails(emails);
        }
        if (parameters.containsKey("Token")) {
            String tokenStashId = parameters.get("Token");
            List<Token> tokens = Context.getStash().get(tokenStashId, List.class);
            user.setTokens(tokens);
        }
        user.create();
        Context.getStash().put(userStashId, user);
    }
}