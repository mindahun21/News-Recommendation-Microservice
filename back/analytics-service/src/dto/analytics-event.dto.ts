export class EngagementDto {
  userId: string;
  resourceId: string;
  action: 'VIEW' | 'CLICK' | 'SHARE' | 'LIKE';
  durationSeconds: number;
}

export class MetricDto {
  metricName: string;
  value: number;
  dimensions: Record<string, string>;
}

export class AnalyticsEventDto {
  eventId: string;
  eventType: 'USER_ENGAGEMENT' | 'SYSTEM_METRIC';
  timestamp: string;
  engagement?: EngagementDto;
  metric?: MetricDto;
}
