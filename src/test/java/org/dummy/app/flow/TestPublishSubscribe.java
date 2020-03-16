package org.dummy.app.flow;

import org.dummy.app.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;


import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class TestPublishSubscribe
{
    @Test
    public void whenSubscribeToIt_thenShouldConsumeAll()
     {

        // given
        SubmissionPublisher<Record<User>> publisher = new SubmissionPublisher<>();
        EndSubscriber<Record<User>> subscriber = new EndSubscriber<>();
        publisher.subscribe(subscriber);
        List<Record<User>> items = new ArrayList<>();
        items.add(
            new Record<>( new User(1,"Naysson"), State.CREATE)
        );
        items.add(
            new Record<>( new User(2,"Lolo"), State.CREATE)
        );

        // when
        assertThat(publisher.getNumberOfSubscribers()).isEqualTo(1);
        items.forEach(publisher::submit);
        publisher.close();

        // then
         await().atMost(1000, TimeUnit.MILLISECONDS)
             .until(
                 () -> subscriber.consumedElements.containsAll(items)
             );
    }
}
