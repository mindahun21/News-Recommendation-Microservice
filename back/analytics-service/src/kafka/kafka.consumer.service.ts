import { Injectable, OnModuleInit } from '@nestjs/common';
import { Kafka, Consumer } from 'kafkajs';
import { AnalyticsService } from '../analytics/analytics.service';
import { AnalyticsEventDto } from 'src/dto/analytics-event.dto';

@Injectable()
export class KafkaConsumerService implements OnModuleInit {
  private readonly kafka: Kafka;
  private readonly consumer: Consumer;

  constructor(private readonly analyticsService: AnalyticsService) {
    this.kafka = new Kafka({
      clientId: 'analytics-service',
      brokers: ['localhost:29092'],
    });
    this.consumer = this.kafka.consumer({
      groupId: 'analytics-consumer-group',
    });
  }

  async onModuleInit() {
    await this.consumer.connect();
    await this.consumer.subscribe({
      topic: 'enriched_news',
      fromBeginning: true,
    });

    await this.consumer.run({
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      eachMessage: async ({ topic: _topic, partition, message }) => {
        if (!message.value) {
          console.warn('no value');
          return;
        }
        console.log({
          partition,
          offset: message.offset,
          value: message.value.toString(),
        });
        const eventDto = JSON.parse(
          message.value.toString(),
        ) as AnalyticsEventDto;
        await this.analyticsService.create(eventDto);
      },
    });
  }
}
