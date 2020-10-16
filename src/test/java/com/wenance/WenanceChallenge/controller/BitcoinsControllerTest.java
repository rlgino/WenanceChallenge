package com.wenance.WenanceChallenge.controller;

import com.wenance.WenanceChallenge.business.BitcoinsFinder;
import com.wenance.WenanceChallenge.dto.ChallengeRequest;
import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BitcoinsControllerTest {

    @MockBean
    BitcoinsFinder finder;

    @Autowired
    private BitcoinsController controller;

    @Test
    public void findBitcoinPrice_ShouldBeSuccess() throws ParseException {
        // setup
        final Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2020-10-15 16:18:53.561");
        final BitcoinSnapshot snapshot = new BitcoinSnapshot();
        snapshot.setId(1);
        snapshot.setPrice(BigDecimal.TEN);
        snapshot.setCreationDateTime(date);
        when(finder.findBitcoinPrice(date)).thenReturn(Optional.of(snapshot));

        // Execute
        final ChallengeRequest request = new ChallengeRequest();
        request.setPattern("yyyy-MM-dd HH:mm:ss.SSS");
        request.setDate("2020-10-15 16:18:53.561");

        final ResponseEntity<Object> response = controller.getPriceOnDate(request);

        // Verify
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        BitcoinSnapshot snapshotResult = (BitcoinSnapshot) response.getBody();
        assertThat(snapshotResult).isNotNull();
        assertThat(snapshotResult.getPrice()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void findBitcoinPrice_ShouldBeNoContent() throws ParseException {
        // setup
        final Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2020-10-15 16:18:53.561");
        when(finder.findBitcoinPrice(date)).thenReturn(Optional.empty());

        // Execute
        final ChallengeRequest request = new ChallengeRequest();
        request.setPattern("yyyy-MM-dd HH:mm:ss.SSS");
        request.setDate("2020-10-15 16:18:53.561");

        final ResponseEntity<Object> response = controller.getPriceOnDate(request);

        // Verify
        assertThat(response.getStatusCode().value()).isEqualTo(204);
        BitcoinSnapshot snapshotResult = (BitcoinSnapshot) response.getBody();
        assertThat(snapshotResult).isNull();
    }

    @Test
    public void findBitcoinPrice_ShouldBeFailParser() throws ParseException {
        // setup
        final ChallengeRequest request = new ChallengeRequest();
        request.setPattern("yyyy-MM-dd HH:mm:ss.SSS");
        request.setDate("2020-10-15 14:22:1");

        // Execute
        final ResponseEntity<Object> response = controller.getPriceOnDate(request);

        // Verify
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().toString()).isEqualTo("Error in data input: Unparseable date: \"2020-10-15 14:22:1\"");
    }

    @Test
    public void findBitcoinPrice_ShouldBeFailPattern() throws ParseException {
        // setup
        final ChallengeRequest request = new ChallengeRequest();
        request.setPattern("");

        // Execute
        final ResponseEntity<Object> response = controller.getPriceOnDate(request);

        // Verify
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().toString()).isEqualTo("The field patter is required");
    }

    @Test
    public void findBitcoinPrice_ShouldBeFailDate() throws ParseException {
        // setup
        final ChallengeRequest request = new ChallengeRequest();
        request.setPattern("yyyy-MM-dd");

        // Execute
        final ResponseEntity<Object> response = controller.getPriceOnDate(request);

        // Verify
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().toString()).isEqualTo("The field date is required");
    }
}
