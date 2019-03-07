package com.mobiquityinc.parser.impl;

import com.mobiquityinc.configuration.PackerConfigurationProvider;
import com.mobiquityinc.parser.PackerInputValidator;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PackerInputValidatorImpl implements PackerInputValidator {
    private static final String ANY_QUANTITY_OF_DIGITS_REGEX = "\\d+";
    private static final String CAPTURING_GROUP_START_REGEX_TOKEN = "(";
    private static final String CAPTURING_GROUP_END_REGEX_TOKEN = ")";
    private static final String DISJUNCTION_REGEX_TOKEN = "|";
    private static final String OPTIONAL_REGEX_TOKEN = "?";
    private static final String ANY_QUANTITY_REGEX_TOKEN = "+";
    private final PackerConfigurationProvider configurationProvider;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSyntaxValid(String line) {
        var pattern = buildLinePattern();
        return line.matches(pattern);
    }

    /**
     * A method for build regular expression for check is provided line is correct
     * @return pattern for checking
     */
    private String buildLinePattern() {
        final var configuration = configurationProvider.getConfiguration();
        final var integerNumberGroupRegex = createRegexGroup(ANY_QUANTITY_OF_DIGITS_REGEX);
        final var onlyFloatNumberGroupRegex = createRegexGroup(
                ANY_QUANTITY_OF_DIGITS_REGEX +
                        Pattern.quote(configuration.getDecimalSeparator()) +
                        ANY_QUANTITY_OF_DIGITS_REGEX
        );
        final var integerOrFloatGroup = createRegexGroup(onlyFloatNumberGroupRegex + DISJUNCTION_REGEX_TOKEN + integerNumberGroupRegex);
        final var itemPropertySeparatorEscaped = Pattern.quote(configuration.getItemPropertySeparator());
        final var itemGroup = createRegexGroup(
                Pattern.quote(configuration.getItemBlockStartToken()) +
                        integerNumberGroupRegex +
                        itemPropertySeparatorEscaped +
                        integerOrFloatGroup +
                        itemPropertySeparatorEscaped +
                        Pattern.quote(configuration.getCurrencySymbol()) +
                        integerOrFloatGroup +
                        Pattern.quote(configuration.getItemBlockEndToken())
        );
        final var allItemsGroup = createRegexGroup(createRegexGroup(
                itemGroup +
                        Pattern.quote(configuration.getItemSeparator()) +
                        OPTIONAL_REGEX_TOKEN
        ) + ANY_QUANTITY_REGEX_TOKEN);
        return createRegexGroup(
                integerNumberGroupRegex +
                        Pattern.quote(configuration.getWeightContentDelimiter()) +
                        allItemsGroup
        );
    }

    /**
     * A method for create RegEx group
     * @param groupContent content of group
     * @return wrapped to group tokens content
     */
    private String createRegexGroup(String groupContent) {
        return CAPTURING_GROUP_START_REGEX_TOKEN + groupContent + CAPTURING_GROUP_END_REGEX_TOKEN;
    }
}
