import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { AnalyticsEventDto } from '../dto/analytics-event.dto';
import { AnalyticsEvent } from '../schemas/analytics-event.schema';

@Injectable()
export class AnalyticsService {
  constructor(
    @InjectModel(AnalyticsEvent.name)
    private readonly analyticsEventModel: Model<AnalyticsEvent>,
  ) {}

  async create(analyticsEventDto: AnalyticsEventDto): Promise<AnalyticsEvent> {
    const createdEvent = new this.analyticsEventModel({
      ...analyticsEventDto,
      timestamp: new Date(analyticsEventDto.timestamp),
    });
    return createdEvent.save();
  }

  async findAll(): Promise<AnalyticsEvent[]> {
    return this.analyticsEventModel.find().exec();
  }
}
