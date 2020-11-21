package com.Gauger;

import java.io.Serializable;

public class AutomationCommand implements Serializable
{
    private CommandType commandType;

    public enum CommandType
    {
        Retrieve,
        New,
        Sort,
        Add
    }

    private CDModel cdToRetrieve;

    public static AutomationCommand Retrieve(CDModel cd)
    {
        AutomationCommand command = new AutomationCommand();
        command.cdToRetrieve = cd;
        command.commandType = CommandType.Retrieve;
        return command;
    }

    public CommandType getCommandType()
    {
        return commandType;
    }

    public CDModel getCdToRetrieve()
    {
        return cdToRetrieve;
    }




}
