package ru.andnovikov.ijustwannarun.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.andnovikov.ijustwannarun.web.rest.TestUtil;

public class DistanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Distance.class);
        Distance distance1 = new Distance();
        distance1.setId("id1");
        Distance distance2 = new Distance();
        distance2.setId(distance1.getId());
        assertThat(distance1).isEqualTo(distance2);
        distance2.setId("id2");
        assertThat(distance1).isNotEqualTo(distance2);
        distance1.setId(null);
        assertThat(distance1).isNotEqualTo(distance2);
    }
}
