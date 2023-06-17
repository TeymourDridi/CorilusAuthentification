package tn.corilus.authentification.authentification.service;

import tn.corilus.authentification.authentification.entities.*;

/**
 * Conversation CRUD
 *
 * @author Teymour Dridi
 */
public interface MessageService {

    Message createMessage(Conversation conversation, User sender, String content);
}
