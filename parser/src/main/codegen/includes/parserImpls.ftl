SqlCreate SqlCreateExtended(Span s, boolean replace) :
{
    final SqlCreate create;
    boolean isTemporary = false;
}
{
    [
        <TEMPORARY> { isTemporary = true; }
    ]
    (
        create = SqlCreateFunction(s, replace, isTemporary)
    )
    {
        return create;
    }
}

SqlCreate SqlCreateFunction(Span s, boolean replace, boolean isTemporary) :
{
    SqlIdentifier functionIdentifier = null;
    SqlCharStringLiteral functionClassName = null;
    String functionLanguage = null;
    boolean ifNotExists = false;
    boolean isSystemFunction = false;
}
{
    (
        <SYSTEM>
        {
            if (!isTemporary){
                throw SqlUtil.newContextException(getPos(),
                ParserResource.RESOURCE.createSystemFunctionOnlySupportTemporary());
            }
        }
        <FUNCTION>
        ifNotExists = IfNotExistsOpt()
        functionIdentifier = SimpleIdentifier()
        { isSystemFunction = true; }
    |
        <FUNCTION>
        ifNotExists = IfNotExistsOpt()
        functionIdentifier = CompoundIdentifier()
    )

    <AS> <QUOTED_STRING> {
        String p = SqlParserUtil.parseString(token.image);
        functionClassName = SqlLiteral.createCharString(p, getPos());
    }
    [<LANGUAGE>
        (
            <JAVA>  { functionLanguage = "JAVA"; }
        )
    ]
    {
        return new SqlCreateFunction(s.pos(), functionIdentifier, functionClassName, functionLanguage,
                ifNotExists, isTemporary, isSystemFunction);
    }
}

/**
 * Parses a "IF NOT EXISTS" option, default is false.
 */
boolean IfNotExistsOpt() :
{
}
{
    (
        LOOKAHEAD(3)
        <IF> <NOT> <EXISTS> { return true; }
    |
        { return false; }
    )
}

