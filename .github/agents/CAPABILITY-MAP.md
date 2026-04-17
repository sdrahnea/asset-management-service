# Agent Capability Map

This map routes tasks to the most effective agent based on each agent's declared purpose.

## Primary Routing (for this Java/Spring codebase)

| Agent file | Best use cases | Invoke when |
|---|---|---|
| `engineering-codebase-onboarding-engineer.md` | Fast codebase comprehension, code-path tracing, factual architecture notes | Starting work in unfamiliar modules |
| `engineering-backend-architect.md` | Backend architecture, service boundaries, API/data design, scalability/security | Designing new modules or cross-layer refactors |
| `engineering-software-architect.md` | DDD/pattern decisions, long-term maintainability | Decision affects multiple bounded contexts/layers |
| `engineering-senior-developer.md` | End-to-end feature implementation | Spec is clear and you need production code quickly |
| `engineering-minimal-change-engineer.md` | Smallest safe bugfix diffs | Hotfixes, low-risk patching, no scope creep |
| `engineering-code-reviewer.md` | Correctness/security/perf-focused reviews | Pre-merge review or risk assessment |
| `engineering-database-optimizer.md` | Query/index/schema performance tuning | Slow repository queries, list/filter bottlenecks |
| `engineering-security-engineer.md` | Threat modeling, secure code review, hardening | Security-sensitive changes (uploads, auth, data exposure) |
| `engineering-devops-automator.md` | CI/CD and automation | Pipeline/workflow/release automation tasks |
| `engineering-sre.md` | Reliability, observability, SLO-driven operations | Stability incidents, error budget, uptime work |
| `engineering-technical-writer.md` | README/docs/API/tutorial quality | Docs refresh, release notes, onboarding docs |
| `engineering-git-workflow-master.md` | Branching/rebase/commit strategy | Complex git history or multi-PR sequencing |

## Specialized Engineering Routing

| Agent file | Best use cases | Invoke when |
|---|---|---|
| `engineering-rapid-prototyper.md` | PoCs and fast MVP spikes | Need validation before full implementation |
| `engineering-frontend-developer.md` | Front-end framework and UI performance work | UI complexity exceeds server-side templating |
| `engineering-mobile-app-builder.md` | Native/cross-platform mobile apps | Mobile client scope is added |
| `engineering-ai-engineer.md` | AI/ML features and model integration | AI capability is in feature scope |
| `engineering-data-engineer.md` | ETL/ELT and data platform pipelines | Reporting/data pipeline initiatives |
| `engineering-ai-data-remediation-engineer.md` | Automated anomaly remediation in data flows | Data quality failures must self-heal |
| `engineering-autonomous-optimization-architect.md` | Continuous optimization with cost/security guardrails | Ongoing API optimization governance |
| `engineering-incident-response-commander.md` | Incident command and response coordination | Active production incident management |
| `engineering-threat-detection-engineer.md` | SIEM detections, MITRE mapping, threat hunting | Security operations detection engineering |
| `engineering-email-intelligence-engineer.md` | Email-to-structured-data extraction | Workflow depends on parsing email threads |
| `engineering-voice-ai-integration-engineer.md` | Speech-to-text pipeline integration | Audio ingestion/transcription features |
| `engineering-cms-developer.md` | WordPress/Drupal implementation | CMS integration needed |
| `engineering-filament-optimization-specialist.md` | Filament admin optimization | Filament PHP admin is in use |
| `engineering-feishu-integration-developer.md` | Feishu/Lark integrations | Feishu ecosystem integration required |
| `engineering-wechat-mini-program-developer.md` | WeChat Mini Program development | WeChat channel support required |
| `engineering-solidity-smart-contract-engineer.md` | Smart contracts and EVM protocol work | Blockchain scope exists |
| `engineering-embedded-firmware-engineer.md` | Firmware/RTOS/MCU development | Hardware/embedded scope exists |

## Product / PM / Delivery Routing

| Agent file | Best use cases | Invoke when |
|---|---|---|
| `product-manager.md` | Product strategy and roadmap shaping | Define "what/why" before engineering execution |
| `product-sprint-prioritizer.md` | Sprint planning and priority decisions | Backlog slicing for current sprint |
| `product-feedback-synthesizer.md` | Aggregate user feedback into priorities | Post-demo/UAT feedback triage |
| `product-trend-researcher.md` | Market and competitive trend insights | Strategic planning and opportunity scans |
| `project-manager-senior.md` | Convert specs into implementation tasks | Need execution plan from requirements docs |
| `project-management-jira-workflow-steward.md` | Jira/Git traceability and release-safe workflow | Compliance-heavy delivery workflows |
| `project-management-project-shepherd.md` | Cross-team execution coordination | Multi-team project orchestration |
| `project-management-experiment-tracker.md` | Experiment planning and tracking | A/B testing or hypothesis-driven work |
| `project-management-studio-operations.md` | Operational process efficiency | Team throughput/process optimization |
| `project-management-studio-producer.md` | Portfolio-level orchestration | Multiple project streams need governance |

## Design Routing

| Agent file | Best use cases | Invoke when |
|---|---|---|
| `design-ux-architect.md` | UX technical architecture and implementation guidance | Defining UX structure and interaction systems |
| `design-ui-designer.md` | Visual systems and component-level UI consistency | UI refresh or design-system alignment |
| `design-ux-researcher.md` | Usability research and user-behavior findings | Need evidence before UX changes |
| `design-brand-guardian.md` | Brand consistency and positioning in UI/content | External-facing brand consistency matters |
| `design-visual-storyteller.md` | Narrative visual communication | Need explanatory visuals for complex concepts |
| `design-image-prompt-engineer.md` | High-quality AI image prompt creation | Generating marketing/product visuals via GenAI |
| `design-inclusive-visuals-specialist.md` | Bias-aware inclusive visual standards | Accessibility/representation-sensitive visuals |
| `design-whimsy-injector.md` | Delight/personality in user interactions | Add playful moments without harming UX clarity |

## Sales Routing

| Agent file | Best use cases | Invoke when |
|---|---|---|
| `sales-engineer.md` | Technical pre-sales discovery and demo alignment | Preparing technical sales narratives |
| `sales-deal-strategist.md` | Deal qualification and win strategy | Managing complex enterprise deals |
| `sales-proposal-strategist.md` | Proposal/RFP strategy and narratives | Responding to formal procurement |
| `sales-pipeline-analyst.md` | Pipeline risk and velocity analysis | Forecast health checks |
| `sales-account-strategist.md` | Post-sale growth/retention strategy | Land-and-expand planning |
| `sales-coach.md` | Rep coaching and execution improvement | Sales enablement cadence |
| `sales-discovery-coach.md` | Discovery call quality and process coaching | Improve qualification quality |
| `sales-outbound-strategist.md` | ICP definition and outbound strategy | Pipeline generation motion |

## Fast Invocation Rules

1. Understand first: `engineering-codebase-onboarding-engineer.md`.
2. Design next: `engineering-backend-architect.md` or `engineering-software-architect.md`.
3. Build: `engineering-senior-developer.md`.
4. Hotfix: `engineering-minimal-change-engineer.md`.
5. Validate: `engineering-code-reviewer.md` (+ `engineering-security-engineer.md` if sensitive).
6. Stabilize: `engineering-database-optimizer.md` for query issues, `engineering-sre.md` for runtime reliability.

