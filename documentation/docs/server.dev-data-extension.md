---
id: server.dev-data-extension
title: "Developing Data extension"
date: "2017-10-20"
order: 3500
hide: false
draft: false
---

A data extension introduces data into the Invest server. 

To create a data extension you need to create a maven project, [as a Invest plugin](server.dev-maven.html), which contains [an extension](server.extension-points.html) implementing `InvestDataExtension`.

This should introduce, via `@ComponentScan`, `@Import` or `@Bean` a set of `DataProviderFactories` as discussed in [Data](server.data.html).

