package org.dummy.app.view.model;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.dummy.app.flow.Record;
import org.dummy.app.model.User;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Log4j2
public class UserListTable extends AbstractTableModel
{

    private List<User> users;

    private static final String[] HEADERS = {"id","login","password"};

    private static UserListTable instance;

    public static UserListTable getInstance(@NonNull Collection<User> users)
    {
        if( instance == null)
        {
            instance = new UserListTable(users);
        }

        return instance;

    }

    private UserListTable(Collection<User> users) {
        this.users = new ArrayList<>(users);
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return HEADERS.length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        User currentUser = users.get(i);
        switch(i1)
        {
            case 0 :
                return currentUser.getId();

            case 1 :
                return currentUser.getLogin();

            case 2 :
                return "******";

            default :
                throw new IndexOutOfBoundsException("Column index out of range.");
        }
    }

    public synchronized void updateTable(@NonNull Record<User> notification) throws IllegalArgumentException
    {
        log.info("Consuming record {}",notification);
        User concernedUser = notification.getConcernedObject();
        switch(notification.getActionToDo())
        {
            case CREATE:
                users.add(concernedUser);
                break;

            case UPDATE:
                users.remove(concernedUser);
                users.add(concernedUser);
                break;

            case DELETE:
                users.remove(concernedUser);
                break;

            default:
                throw new IllegalArgumentException("State unknown");
        }
        fireTableDataChanged();
        log.info("Consumed record : {}",notification);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return HEADERS[column];
    }
}
