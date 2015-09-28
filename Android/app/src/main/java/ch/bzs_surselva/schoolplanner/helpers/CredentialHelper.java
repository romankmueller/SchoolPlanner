package ch.bzs_surselva.schoolplanner.helpers;

public final class CredentialHelper
{
    private static String account;
    private static String password;

    public static String getAccount()
    {
        return CredentialHelper.account;
    }

    public static void setAccount(String account)
    {
        CredentialHelper.account = account;
    }

    public static String getPassword()
    {
        return CredentialHelper.password;
    }

    public static void setPassword(String password)
    {
        CredentialHelper.password = StringHelper.nullToEmpty(password);
    }
}


