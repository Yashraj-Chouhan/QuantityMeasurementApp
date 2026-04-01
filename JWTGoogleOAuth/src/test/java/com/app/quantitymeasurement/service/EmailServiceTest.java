package com.app.quantitymeasurement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * EmailServiceTest
 *
 * Unit tests for {@link EmailService} using Mockito.
 * {@link JavaMailSender} is mocked so that no real SMTP connection is
 * attempted during the test run.
 *
 * <p>Each send method is tested for two behaviours:</p>
 * <ol>
 *   <li><b>Happy path</b> — {@code mailSender.send()} is called exactly once
 *       with a {@link SimpleMailMessage} addressed to the expected recipient.</li>
 *   <li><b>SMTP failure</b> — when {@code mailSender.send()} throws a
 *       {@link MailSendException}, the exception is caught internally and does
 *       <em>not</em> propagate to the caller.  Email sending is fire-and-forget;
 *       a transient SMTP outage must never break the primary API response.</li>
 * </ol>
 *
 * <p><b>Note on {@code @Async}:</b> the {@code @Async} annotation on each
 * send method is transparent to Mockito-based unit tests.  The methods execute
 * synchronously here, which is the correct behaviour for unit tests — we want
 * deterministic verification, not background thread scheduling.</p>
 *
 * @author UC19
 * @version 19.0
 * @since 19.0
 */
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    private static final String FROM    = "noreply@qmapp.com";
    private static final String TO      = "user@example.com";
    private static final String TO_NAME = "Alice";

    @BeforeEach
    void setUp() {
        /*
         * The @Value("${spring.mail.username}") field is not injected when
         * running outside a Spring context. ReflectionTestUtils sets it
         * directly so we can assert the From address without a full context.
         */
        ReflectionTestUtils.setField(emailService, "fromAddress", FROM);
    }

    // =========================================================================
    // sendRegistrationEmail
    // =========================================================================

    @Test
    void testSendRegistrationEmail_InvokesSend_Once() {
        emailService.sendRegistrationEmail(TO, TO_NAME);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendRegistrationEmail_MessageAddressedToRecipient() {
        emailService.sendRegistrationEmail(TO, TO_NAME);

        ArgumentCaptor<SimpleMailMessage> captor =
            ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        SimpleMailMessage msg = captor.getValue();
        assertArrayEquals(new String[]{TO}, msg.getTo());
        assertEquals(FROM, msg.getFrom());
    }

    @Test
    void testSendRegistrationEmail_SubjectContainsWelcome() {
        emailService.sendRegistrationEmail(TO, TO_NAME);

        ArgumentCaptor<SimpleMailMessage> captor =
            ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        assertNotNull(captor.getValue().getSubject());
        assertTrue(
            captor.getValue().getSubject().toLowerCase().contains("welcome"),
            "Subject should mention 'Welcome'"
        );
    }

    @Test
    void testSendRegistrationEmail_BodyContainsUserName() {
        emailService.sendRegistrationEmail(TO, "Bob");

        ArgumentCaptor<SimpleMailMessage> captor =
            ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        assertNotNull(captor.getValue().getText());
        assertTrue(
            captor.getValue().getText().contains("Bob"),
            "Email body should include the user's display name"
        );
    }

    @Test
    void testSendRegistrationEmail_SmtpFailure_DoesNotPropagate() {
        /*
         * A transient SMTP failure must be swallowed so that a broken mail
         * server cannot prevent the registration response from reaching
         * the client.
         */
        doThrow(new MailSendException("SMTP unavailable"))
            .when(mailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(
            () -> emailService.sendRegistrationEmail(TO, TO_NAME)
        );
    }

    // =========================================================================
    // sendLoginNotificationEmail
    // =========================================================================

    @Test
    void testSendLoginNotificationEmail_InvokesSend_Once() {
        emailService.sendLoginNotificationEmail(TO);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendLoginNotificationEmail_MessageAddressedToRecipient() {
        emailService.sendLoginNotificationEmail(TO);

        ArgumentCaptor<SimpleMailMessage> captor =
            ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        assertArrayEquals(new String[]{TO}, captor.getValue().getTo());
    }

    @Test
    void testSendLoginNotificationEmail_SmtpFailure_DoesNotPropagate() {
        doThrow(new MailSendException("Connection refused"))
            .when(mailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(
            () -> emailService.sendLoginNotificationEmail(TO)
        );
    }

    // =========================================================================
    // sendForgotPasswordEmail
    // =========================================================================

    @Test
    void testSendForgotPasswordEmail_InvokesSend_Once() {
        emailService.sendForgotPasswordEmail(TO);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendForgotPasswordEmail_MessageAddressedToRecipient() {
        emailService.sendForgotPasswordEmail(TO);

        ArgumentCaptor<SimpleMailMessage> captor =
            ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        assertArrayEquals(new String[]{TO}, captor.getValue().getTo());
        assertEquals(FROM, captor.getValue().getFrom());
    }

    @Test
    void testSendForgotPasswordEmail_SmtpFailure_DoesNotPropagate() {
        doThrow(new MailSendException("Timeout"))
            .when(mailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(
            () -> emailService.sendForgotPasswordEmail(TO)
        );
    }

    // =========================================================================
    // sendPasswordResetEmail
    // =========================================================================

    @Test
    void testSendPasswordResetEmail_InvokesSend_Once() {
        emailService.sendPasswordResetEmail(TO);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendPasswordResetEmail_MessageAddressedToRecipient() {
        emailService.sendPasswordResetEmail(TO);

        ArgumentCaptor<SimpleMailMessage> captor =
            ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        assertArrayEquals(new String[]{TO}, captor.getValue().getTo());
        assertEquals(FROM, captor.getValue().getFrom());
    }

    @Test
    void testSendPasswordResetEmail_SmtpFailure_DoesNotPropagate() {
        doThrow(new MailSendException("Auth failed"))
            .when(mailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(
            () -> emailService.sendPasswordResetEmail(TO)
        );
    }

    // =========================================================================
    // From address
    // =========================================================================

    @Test
    void testAllSendMethods_UseConfiguredFromAddress() {
        /*
         * Verifies that every send method uses the injected SMTP username
         * as the From address, rather than a hardcoded string.
         */
        emailService.sendRegistrationEmail(TO, TO_NAME);
        emailService.sendLoginNotificationEmail(TO);
        emailService.sendForgotPasswordEmail(TO);
        emailService.sendPasswordResetEmail(TO);

        ArgumentCaptor<SimpleMailMessage> captor =
            ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(4)).send(captor.capture());

        captor.getAllValues().forEach(msg ->
            assertEquals(FROM, msg.getFrom(),
                "Every message must originate from the configured SMTP username")
        );
    }
}
