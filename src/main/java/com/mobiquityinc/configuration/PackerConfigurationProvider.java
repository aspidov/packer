package com.mobiquityinc.configuration;

import com.mobiquityinc.model.PackerConfiguration;

/**
 * Inteface which describes configuration for packer
 */
public interface PackerConfigurationProvider {
    /**
     * Method for retrieve configuration
     *
     * @return configuration of packer
     */
    PackerConfiguration getConfiguration();
}
