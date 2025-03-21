package org.pt.service;

import org.pt.entity.KafkaBindingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KafkaConfigService {

    @Value("${spring.cloud.function.definition}")
    private String functionDefinitions;

    @Autowired
    private BindingServiceProperties bindingServiceProperties;

//    public List<KafkaBindingInfo> getKafkaConfig() {
//        List<KafkaBindingInfo> kafkaBindingInfos = new ArrayList<>();
//        Map<String, org.springframework.cloud.stream.config.BindingProperties> bindings = bindingServiceProperties.getBindings();
//        System.out.println(bindings.toString());
//        // Split function definitions by semicolon
//        for (String function : functionDefinitions.split(";")) {
//            if (function.isEmpty()) continue;
//            // Handle input and output bindings
//            processBinding(function, "-in-0", "input", bindings, kafkaBindingInfos);
//            processBinding(function, "-out-0", "output", bindings, kafkaBindingInfos);
//        }
//        return kafkaBindingInfos;
//    }
//
//    private void processBinding(String function, String bindingSuffix, String bindingType,
//                                Map<String, org.springframework.cloud.stream.config.BindingProperties> bindings,
//                                List<KafkaBindingInfo> kafkaBindingInfos) {
//        String bindingName = function + bindingSuffix;
//        if (bindings.containsKey(bindingName)) {
//            org.springframework.cloud.stream.config.BindingProperties props = bindings.get(bindingName);
//            KafkaBindingInfo info = new KafkaBindingInfo();
//            info.setFunctionName(function);
//            info.setBindingType(bindingType);
//            info.setDestination(props.getDestination());
//            info.setGroup(props.getGroup() != null ? props.getGroup() : "");
//
//            if ("input".equals(bindingType)) {
//                if (props.getConsumer() != null) {
//                    info.setConcurrency(props.getConsumer().getConcurrency());
//                    info.setBatchMode(props.getConsumer().isBatchMode());
//                    info.setMaxAttempts(props.getConsumer().getMaxAttempts());
//                } else {
//                    info.setConcurrency(1);
//                    info.setBatchMode(false);
//                    info.setMaxAttempts(0);
//                }
//                info.setPartitionCount(null); // Partition count not applicable for input binding
//            } else if ("output".equals(bindingType)) {
//                if (props.getProducer() != null) {
//                    info.setPartitionCount(props.getProducer().getPartitionCount());
//                } else {
//                    info.setPartitionCount(0);
//                }
//                // Output binding does not apply to consumer properties
//                info.setConcurrency(null);
//                info.setBatchMode(null);
//                info.setMaxAttempts(null);
//            }
//            info.setErrorHandler(props.getErrorHandlerDefinition() != null ? props.getErrorHandlerDefinition() : "");
//            kafkaBindingInfos.add(info);
//        }
//    }

    public Map<String, BindingProperties> getKafkaConfigAll() {
        return bindingServiceProperties.getBindings();
    }
}