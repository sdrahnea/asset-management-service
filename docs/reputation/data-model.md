# Reputation — Data Model

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `reputation`

`Reputation` extends `CoreEntity`; base `name` maps to `reputation_id`.

## Unique Constraints
- `reputation_id`
- `(entity_type, entity_id)`

## Field Groups
### Identity and Context
- `reputationId`, `entityType`, `entityId`, `displayName`, `industry`, `geographicScope`

### Reputation Signals
- `publicSentiment`, `socialMediaSentiment`, `customerFeedbackScore`, `employeeFeedbackScore`, `stakeholderFeedback`, `mediaCoverageVolume`, `mediaCoverageTone`

### Reputation Events
- `eventType`, `eventDate`, `eventDescription`, `eventImpactScore`, `eventSource`

### Quantitative Indicators
- `reputationScore`, `trustScore`, `credibilityScore`, `riskScore`, `volatilityIndex`, `trendDirection`

### Risk and Compliance
- `legalIssues`, `ethicalConcerns`, `complianceFlags`, `crisisHistory`, `recoveryPerformance`

### External Context
- `marketReputationBenchmark`, `competitivePosition`, `culturalFactors`, `regulatoryEnvironment`

### Supporting Evidence
- `referencesData`, `reviewSamples`, `mediaArticles`, `surveyData`, `internalNotes`

### Operational Metadata
- `dataSource`, `responsibleAgent`, `tags`, `notes`

## Key Rules
- Mandatory: reputationId, entityType, entityId, displayName, dataSource
- Event rule: if event details are present, eventType is mandatory
- Date rule: eventDate cannot be in the future
- Score/sentiment range checks per field annotation

