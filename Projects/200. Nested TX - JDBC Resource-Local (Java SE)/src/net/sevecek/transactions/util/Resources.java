package net.sevecek.transactions.util;

public class Resources {

    public static final String NUMBER_IN_DB_BEFORE_ANY_TRANSACTION = "Number in DB before any transaction ";

    public static final String BEGIN_TOP_LEVEL_TRANSACTION                   = "    beginTopLevelTransaction()";
    public static final String TOP_LEVEL_TRANSACTION_JUST_UPDATED_VALUE = "    Top-level transaction just updated value ";

    public static final String BEGIN_NESTED_TRANSACTION = "        beginNestedTransaction()";
    public static final String NESTED_OR_PARALLEL_TRANSACTION_CAN_READ_VALUE = "        Nested or parallel transaction can read value ";
    public static final String NESTED_OR_PARALLEL_TRANSACTION_UPDATES_VALUE = "        Nested or parallel transaction updates value ";
    public static final String COMMIT_NESTED_TRANSACTION = "        commitNestedTransaction()";
    public static final String ROLLBACK_NESTED_TRANSACTION = "        rollbackNestedTransaction()";

    public static final String TOP_LEVEL_TRANSACTION_CAN_READ_VALUE = "    Top-level transaction can read value ";
    public static final String ROLLBACK_TOP_LEVEL_TRANSACTION = "    rollbackTopLevelTransaction()";
    public static final String COMMIT_TOP_LEVEL_TRANSACTION = "    commitTopLevelTransaction()";

    public static final String NUMBER_IN_DB_AFTER_ALL_TRANSACTIONS = "Number in DB after all transactions ";
}
