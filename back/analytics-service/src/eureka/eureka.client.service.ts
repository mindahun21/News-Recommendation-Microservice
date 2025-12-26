import { Injectable, OnModuleInit } from '@nestjs/common';
import { Eureka } from 'eureka-js-client';

@Injectable()
export class EurekaClientService implements OnModuleInit {
  private readonly eurekaClient: Eureka;

  constructor() {
    // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment, @typescript-eslint/no-unsafe-call
    this.eurekaClient = new Eureka({
      instance: {
        app: 'analytics-service',
        hostName: 'localhost',
        ipAddr: '127.00.1',
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
    // eslint-disable-next-line @typescript-eslint/no-unsafe-call, @typescript-eslint/no-unsafe-member-access
    this.eurekaClient.start((error: Error | undefined) => {
      if (error) {
        console.error('Eureka client failed to start:', error);
      } else {
        console.log('Eureka client started');
      }
    });
  }
}
