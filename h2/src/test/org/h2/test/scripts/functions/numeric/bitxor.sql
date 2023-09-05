-- Copyright 2004-2023 H2 Group. Multiple-Licensed under the MPL 2.0,
-- and the EPL 1.0 (https://h2database.com/html/license.html).
-- Initial Developer: H2 Group
--

select bitxor(null, 1) vn, bitxor(1, null) vn1, bitxor(null, null) vn2, bitxor(3, 6) e5;
> VN   VN1  VN2  E5
> ---- ---- ---- --
> null null null 5
> rows: 1

SELECT BITXOR(10, 12);
>> 6

SELECT BITXNOR(10, 12);
>> -7

CREATE TABLE TEST(A BIGINT, B BIGINT);
> ok

EXPLAIN SELECT BITNOT(BITXOR(A, B)), BITNOT(BITXNOR(A, B)) FROM TEST;
>> SELECT BITXNOR("A", "B"), BITXOR("A", "B") FROM "PUBLIC"."TEST" /* PUBLIC.TEST.tableScan */

DROP TABLE TEST;
> ok

EXPLAIN SELECT
    BITXOR(CAST((0xC5 - 0x100) AS TINYINT), CAST(0x63 AS TINYINT)),
    BITXOR(CAST(0xC5 AS SMALLINT), CAST(0x63 AS SMALLINT)),
    BITXOR(CAST(0xC5 AS INTEGER), CAST(0x63 AS INTEGER)),
    BITXOR(CAST(0xC5 AS BIGINT), CAST(0x63 AS BIGINT)),
    BITXOR(CAST(X'C5' AS VARBINARY), CAST(X'63' AS VARBINARY)),
    BITXOR(CAST(X'C5' AS BINARY), CAST(X'63' AS BINARY));
>> SELECT CAST(-90 AS TINYINT), CAST(166 AS SMALLINT), 166, CAST(166 AS BIGINT), X'a6', CAST(X'a6' AS BINARY(1))

EXPLAIN SELECT
    BITXOR(CAST(X'C501' AS VARBINARY), CAST(X'63' AS VARBINARY)),
    BITXOR(CAST(X'63' AS VARBINARY), CAST(X'C501' AS VARBINARY)),
    BITXOR(CAST(X'C501' AS BINARY(2)), CAST(X'63' AS BINARY)),
    BITXOR(CAST(X'63' AS BINARY), CAST(X'C501' AS BINARY(2)));
>> SELECT X'a601', X'a601', CAST(X'a601' AS BINARY(2)), CAST(X'a601' AS BINARY(2))

EXPLAIN SELECT
    BITXOR(CAST(X'C501' AS VARBINARY), CAST(X'63' AS BINARY)),
    BITXOR(CAST(X'63' AS BINARY), CAST(X'C501' AS VARBINARY));
>> SELECT CAST(X'a6' AS BINARY(1)), CAST(X'a6' AS BINARY(1))

EXPLAIN SELECT
    BITXNOR(CAST((0xC5 - 0x100) AS TINYINT), CAST(0x63 AS TINYINT)),
    BITXNOR(CAST(0xC5 AS SMALLINT), CAST(0x63 AS SMALLINT)),
    BITXNOR(CAST(0xC5 AS INTEGER), CAST(0x63 AS INTEGER)),
    BITXNOR(CAST(0xC5 AS BIGINT), CAST(0x63 AS BIGINT)),
    BITXNOR(CAST(X'C5' AS VARBINARY), CAST(X'63' AS VARBINARY)),
    BITXNOR(CAST(X'C5' AS BINARY), CAST(X'63' AS BINARY));
>> SELECT CAST(89 AS TINYINT), CAST(-167 AS SMALLINT), -167, CAST(-167 AS BIGINT), X'59', CAST(X'59' AS BINARY(1))

EXPLAIN SELECT
    BITXNOR(CAST(X'C501' AS VARBINARY), CAST(X'63' AS VARBINARY)),
    BITXNOR(CAST(X'63' AS VARBINARY), CAST(X'C501' AS VARBINARY)),
    BITXNOR(CAST(X'C501' AS BINARY(2)), CAST(X'63' AS BINARY)),
    BITXNOR(CAST(X'63' AS BINARY), CAST(X'C501' AS BINARY(2)));
>> SELECT X'59fe', X'59fe', CAST(X'59fe' AS BINARY(2)), CAST(X'59fe' AS BINARY(2))

EXPLAIN SELECT
    BITXNOR(CAST(X'C501' AS VARBINARY), CAST(X'63' AS BINARY)),
    BITXNOR(CAST(X'63' AS BINARY), CAST(X'C501' AS VARBINARY));
>> SELECT CAST(X'59' AS BINARY(1)), CAST(X'59' AS BINARY(1))

SELECT BITXOR('AA', 'BB');
> exception INVALID_VALUE_2

SELECT BITXOR(1, X'AA');
> exception INVALID_VALUE_2

SELECT BITXNOR('AA', 'BB');
> exception INVALID_VALUE_2

SELECT BITXNOR(1, X'AA');
> exception INVALID_VALUE_2
