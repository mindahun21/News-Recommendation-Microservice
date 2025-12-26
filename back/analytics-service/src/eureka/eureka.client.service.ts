import { Injectable, OnModuleInit } from '@nestjs/common';
import { Eureka } from 'eureka-js-client';

@Injectable()
export class EurekaClientService implements OnModuleInit {
  private readonly eurekaClient: Eureka;

  constructor() {
    this.eurekaClient = new Eureka({
      instance: {
        app: 'analytics-service',
        hostName: 'localhost',
        ipAddr: '127.0.0.1',
        port: {
          $: 3006, // Assuming this service runs on port 3006
          '@enabled': true,
        },
        vipAddress: 'analytics-service',
        dataCenterInfo: {
          '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
          name: 'MyOwn',
        },
      },
      eureka: {
        host: 'localhost',
        port: 8901,
        servicePath: '/eureka/',
      },
    });
  }

  onModuleInit() {
    this.eurekaClient.start((error) => {
      console.log(error || 'Eureka client started');
    });
  }
}
