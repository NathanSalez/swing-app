package org.dummy.app.flow;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
public class Record<T>
{
    T concernedObject;

    State actionToDo;
}
