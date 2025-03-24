package org.pt.controller;

import org.pt.resp.Response;
import org.pt.service.InstanceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/instances")
public class InstanceController {

    private final InstanceService instanceService;

    public InstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }


    @GetMapping(value = "/all",produces= MediaType.APPLICATION_JSON_VALUE )
    public Response<List<InstanceService.ServiceInstanceInfo>> getAllInstance() {
        return new Response<List<InstanceService.ServiceInstanceInfo>>(200, instanceService.getAllInstances());
    }


    @GetMapping(value = "/stop/{server}",produces= MediaType.APPLICATION_JSON_VALUE )
    public Response<String> stopServer(@PathVariable("server") String server) {
        return new Response<String>(200,"DOWN",instanceService.stopProvider(server));
    }

    @GetMapping(value = "/restart/{server}",produces= MediaType.APPLICATION_JSON_VALUE )
    public Response<String> restartServer(@PathVariable("server") String server) {
        return new Response<String>(200,"UP",instanceService.startProvider(server));
    }

    @GetMapping(value = "/provider-round/getStatus",produces= MediaType.APPLICATION_JSON_VALUE )
    public Response<String> getStatus() {
        return new Response<String>(200,"success",instanceService.getProviderStatus());
    }

}