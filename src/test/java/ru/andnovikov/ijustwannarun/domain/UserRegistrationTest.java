package ru.andnovikov.ijustwannarun.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.andnovikov.ijustwannarun.web.rest.TestUtil;

public class UserRegistrationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRegistration.class);
        UserRegistration userRegistration1 = new UserRegistration();
        userRegistration1.setId("id1");
        UserRegistration userRegistration2 = new UserRegistration();
        userRegistration2.setId(userRegistration1.getId());
        assertThat(userRegistration1).isEqualTo(userRegistration2);
        userRegistration2.setId("id2");
        assertThat(userRegistration1).isNotEqualTo(userRegistration2);
        userRegistration1.setId(null);
        assertThat(userRegistration1).isNotEqualTo(userRegistration2);
    }
}
