package com.Gauger;

import java.io.Serializable;

public class AutomationCommandResult implements Serializable
{
    public AutomationCommand command;
    public String message;

    public AutomationCommandResult(AutomationCommand command, String message)
    {
        this.command = command;
        this.message = message;
    }

}
