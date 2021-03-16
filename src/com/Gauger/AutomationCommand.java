package com.Gauger;

import java.io.Serializable;

public class AutomationCommand implements Serializable
{
    private CommandType commandType;
    private String sortSection;

    public enum CommandType
    {
        Retrieve,
        Return,
        Remove,
        Sort,
        Add
    }

    private CDModel cd;

    public static AutomationCommand Retrieve(CDModel cd)
    {
        AutomationCommand command = new AutomationCommand();
        command.cd = cd;
        command.commandType = CommandType.Retrieve;
        return command;
    }

    public static AutomationCommand Add(CDModel cd)
    {
        AutomationCommand command = new AutomationCommand();
        command.cd = cd;
        command.commandType = CommandType.Add;
        return command;
    }

    public static AutomationCommand Return(CDModel cd)
    {
        AutomationCommand command = new AutomationCommand();
        command.cd = cd;
        command.commandType = CommandType.Return;
        return command;
    }

    public static AutomationCommand Remove(CDModel cd)
    {
        AutomationCommand command = new AutomationCommand();
        command.cd = cd;
        command.commandType = CommandType.Remove;
        return command;
    }

    public static AutomationCommand Sort(String section)
    {
        AutomationCommand command = new AutomationCommand();
        command.commandType = CommandType.Sort;
        command.sortSection = section;
        return command;
    }

    public CommandType getCommandType()
    {
        return commandType;
    }

    public CDModel getCd()
    {
        return cd;
    }

    public String getSortSection() { return sortSection; }

    public String getCommandTypeText()
    {
        switch (commandType)
        {
            case Return:
                return "CD Returned";
            case Remove:
                return "CD Removed";
            case Add:
                return "CD Added";
            case Sort:
                return "CDs Sorted";
            case Retrieve:
                return "CD Retrieved";
            default:
                return "Unknown";
        }
    }
}
