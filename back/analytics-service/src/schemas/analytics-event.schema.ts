import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document } from 'mongoose';

@Schema()
export class Engagement {
  @Prop()
  userId: string;

  @Prop()
  resourceId: string;

  @Prop()
  action: string;

  @Prop()
  durationSeconds: number;
}

@Schema()
export class Metric {
  @Prop()
  metricName: string;

  @Prop()
  value: number;

  @Prop({ type: 'Map', of: String })
  dimensions: Map<string, string>;
}

@Schema({ timestamps: true })
export class AnalyticsEvent extends Document {
  @Prop({ required: true, index: true })
  eventId: string;

  @Prop({ required: true })
  eventType: string;

  @Prop({ required: true })
  timestamp: Date;

  @Prop({ type: Engagement })
  engagement?: Engagement;

  @Prop({ type: Metric })
  metric?: Metric;
}

export const AnalyticsEventSchema =
  SchemaFactory.createForClass(AnalyticsEvent);
