import { Module } from '@nestjs/common';
import { AnalyticsModule } from 'src/analytics/analytics.module';
import { KafkaConsumerService } from './kafka.consumer.service';

@Module({
  imports: [AnalyticsModule],
  providers: [KafkaConsumerService],
})
export class KafkaModule {}
