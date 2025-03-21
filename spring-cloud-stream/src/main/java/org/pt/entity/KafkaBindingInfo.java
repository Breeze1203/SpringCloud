package org.pt.entity;

/**
 * Represents the Kafka binding information for a function.
 */
public class KafkaBindingInfo {
    private String functionName;      // Function name
    private String bindingType;       // Binding type (input or output)
    private String destination;       // Kafka topic
    private String group;             // Consumer group
    private Integer concurrency;      // Concurrency (only applicable for input bindings)
    private Integer partitionCount;   // Number of partitions (only applicable for output bindings)
    private Boolean batchMode;        // Batch mode (only applicable for input bindings)
    private Integer maxAttempts;      // Maximum retry attempts (only applicable for input bindings)
    private String errorHandler;      // Error handler definition

    // Constructors
    public KafkaBindingInfo() {}

    public KafkaBindingInfo(String functionName, String bindingType, String destination, String group,
                            Integer concurrency, Integer partitionCount, Boolean batchMode,
                            Integer maxAttempts, String errorHandler) {
        this.functionName = functionName;
        this.bindingType = bindingType;
        this.destination = destination;
        this.group = group;
        this.concurrency = concurrency;
        this.partitionCount = partitionCount;
        this.batchMode = batchMode;
        this.maxAttempts = maxAttempts;
        this.errorHandler = errorHandler;
    }

    // Getters and Setters
    public String getFunctionName() { return functionName; }
    public void setFunctionName(String functionName) { this.functionName = functionName; }
    public String getBindingType() { return bindingType; }
    public void setBindingType(String bindingType) { this.bindingType = bindingType; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }
    public Integer getConcurrency() { return concurrency; }
    public void setConcurrency(Integer concurrency) { this.concurrency = concurrency; }
    public Integer getPartitionCount() { return partitionCount; }
    public void setPartitionCount(Integer partitionCount) { this.partitionCount = partitionCount; }
    public Boolean getBatchMode() { return batchMode; }
    public void setBatchMode(Boolean batchMode) { this.batchMode = batchMode; }
    public Integer getMaxAttempts() { return maxAttempts; }
    public void setMaxAttempts(Integer maxAttempts) { this.maxAttempts = maxAttempts; }
    public String getErrorHandler() { return errorHandler; }
    public void setErrorHandler(String errorHandler) { this.errorHandler = errorHandler; }
}