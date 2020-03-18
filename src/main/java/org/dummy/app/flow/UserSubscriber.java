package org.dummy.app.flow;

import lombok.extern.log4j.Log4j2;
import org.dummy.app.model.User;
import org.dummy.app.view.model.UserListTable;

import java.util.concurrent.Flow;

@Log4j2
public class UserSubscriber implements Flow.Subscriber<Record<User>>
{
    private UserListTable endPoint;

    private Flow.Subscription subscription;


    public UserSubscriber(UserListTable endPoint) {
        this.endPoint = endPoint;
    }


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Record<User> userRecord) {
        endPoint.updateTable(userRecord);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error(throwable.getMessage(),throwable);
    }

    @Override
    public void onComplete() {
        log.info("End of subscriber.");
    }
}
