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

    @Override
    public boolean isSyntaxValid(String line) {
        var pattern = buildLinePattern();
        return line.matches(pattern);
    }

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

    private String createRegexGroup(String groupContent) {
        return CAPTURING_GROUP_START_REGEX_TOKEN + groupContent + CAPTURING_GROUP_END_REGEX_TOKEN;
    }
}
