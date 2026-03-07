package org.example.model;

import java.util.TreeSet;

public interface BookingManager {
    void bookWorkspace(User user, Workspace workspace)
            throws WorkspaceNotAvailableException, UserNotRegisteredException;
    void cancelBooking(User user, Workspace workspace)
            throws UserNotRegisteredException;
}