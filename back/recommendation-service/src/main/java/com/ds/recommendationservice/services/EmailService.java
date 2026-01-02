package com.ds.recommendationservice.services;

import com.ds.recommendationservice.models.EmailTemplates;
import com.ds.recommendationservice.models.EnrichedNewsPayload;
import com.ds.recommendationservice.models.UserRecommendationProfile;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public Mono<Void> sendNewsRecommendation(UserRecommendationProfile user, EnrichedNewsPayload news, String recipientEmail) {
        return Mono.fromRunnable(() -> {
            try {
                Context context = new Context();
                context.setVariable("user", user);
                context.setVariable("news", news);

                String htmlContent = templateEngine.process(EmailTemplates.RECOMMENDATION.getTemplate(), context);

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(
                        message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name()
                );

                helper.setTo(recipientEmail);
                helper.setSubject("Recommended for you: " + news.getTitle());
                helper.setFrom("news-noreply@newsrecommendation.com");
                helper.setText(htmlContent, true);

                mailSender.send(message);
                log.info("Recommendation email sent successfully to user: {}", user.getUserId());

            } catch (MessagingException e) {
                log.error("Failed to send recommendation email to {}", recipientEmail, e);
                throw new RuntimeException("Email sending failed", e);
            }
        })
        .subscribeOn(Schedulers.boundedElastic())
        .then();
    }

}
