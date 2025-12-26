import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { MongooseModule } from '@nestjs/mongoose';
import { AnalyticsModule } from './analytics/analytics.module';
import { KafkaModule } from './kafka/kafka.module';
import { EurekaClientModule } from './eureka/eureka.client.module';

@Module({
  imports: [
    MongooseModule.forRoot('mongodb://localhost:27017/analytics'),
    AnalyticsModule,
    KafkaModule,
    EurekaClientModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
