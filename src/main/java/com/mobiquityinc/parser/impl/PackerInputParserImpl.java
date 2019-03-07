package com.mobiquityinc.parser.impl;

import com.mobiquityinc.configuration.PackerConfigurationProvider;
import com.mobiquityinc.exception.IncorrectFormatException;
import com.mobiquityinc.model.KnapsackItem;
import com.mobiquityinc.model.KnapsackTask;
import com.mobiquityinc.parser.PackerInputValidator;
import com.mobiquityinc.parser.PackerParserService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.validation.Validation;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PackerInputParserImpl implements PackerParserService {

    private static final int WEIGHT_INDEX_IN_WEIGHT_AND_CONTENT_SPLIT = 0;
    private static final int ITEMS_INDEX_IN_WEIGHT_AND_CONTENT_SPLIT = 1;
    private static final int ITEM_IDX_INDEX_IN_SERIALIZED_ITEM = 0;
    private static final int WEIGHT_INDEX_IN_SERIALIZED_ITEM = 1;
    private static final int COST_INDEX_IN_SERIALIZED_ITEM = 2;

    private final PackerInputValidator inputValidator;
    private final PackerConfigurationProvider configurationProvider;

    public KnapsackTask parseSingleKnapsack(String line) {
        final var task = buildKnapsackTask(line);
        final var validationResult = Validation.buildDefaultValidatorFactory().getValidator().validate(task);
        if (validationResult.isEmpty()) {
            return task;
        }
        final var validationMessage = validationResult.stream()
                .map(constraintViolation -> String.format("%s: %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()))
                .collect(Collectors.joining(","));
        throw new IncorrectFormatException(validationMessage);
    }

    private KnapsackTask buildKnapsackTask(String line) {
        if (!inputValidator.isSyntaxValid(line)) {
            throw new IncorrectFormatException("The syntax of input line is not valid!");
        }
        final var configuration = configurationProvider.getConfiguration();
        final var weightAndItems = line.split(Pattern.quote(configuration.getWeightContentDelimiter()));
        final var weight = Double.valueOf(weightAndItems[WEIGHT_INDEX_IN_WEIGHT_AND_CONTENT_SPLIT]);
        final var itemGroups = weightAndItems[ITEMS_INDEX_IN_WEIGHT_AND_CONTENT_SPLIT];
        final var items = parseGroups(itemGroups);
        return KnapsackTask.builder()
                .weight(weight)
                .items(items)
                .build();
    }

    private Collection<KnapsackItem> parseGroups(String itemGroups) {
        final var configuration = configurationProvider.getConfiguration();
        final var rawSplitItems = itemGroups.split(Pattern.quote(configuration.getItemBlockEndToken())); // this groups will be like "(i,w,c". The leading brace should be deleted
        return Arrays.stream(rawSplitItems)
                .map(item -> item.replaceFirst(Pattern.quote(configuration.getItemBlockStartToken()), "").trim())
                .map(this::parseItem)
                .collect(Collectors.toList());
    }

    private KnapsackItem parseItem(String textItem) {
        final var configuration = configurationProvider.getConfiguration();
        final var splitItemProperties = textItem.split(configuration.getItemPropertySeparator());
        final var index = Long.valueOf(splitItemProperties[ITEM_IDX_INDEX_IN_SERIALIZED_ITEM]);
        final var weight = Double.valueOf(splitItemProperties[WEIGHT_INDEX_IN_SERIALIZED_ITEM]);
        final var costWithCurrency = splitItemProperties[COST_INDEX_IN_SERIALIZED_ITEM];
        final var costWithoutCurrency = costWithCurrency.replaceFirst(Pattern.quote(configuration.getCurrencySymbol()), "");
        final var cost = Double.valueOf(costWithoutCurrency);
        return KnapsackItem.builder()
                .cost(cost)
                .weight(weight)
                .indexNumber(index)
                .build();
    }
}
