---
id: server.faq
title: "FAQ"
date: "2017-10-20"
order: 3010
hide: false
draft: false
---

## Why Java 8? Java 9 has some interesting modularity support that Invest could benefit from.

We plan to support Java 9 over time.

At the time of starting the project Java 9 was only just released. We evaluated Java 9 and found it to be workable. However at that point the ecosystem was immature. The IDE's would not run on Java 9 and even building Java 9 in Maven was a rough experience. 

Secondly we are aware it may take some time for users and organisations to ugrade to Java 9. We didn't want this to be a hurdle to actually using the software. 

Finally, Java 9 is scheduled for end of support before the end of our intitial development period. Thus we wanted to see what Java 10 holds. 

## Why not use X (another plugin system / modularity system)

It appears that Java 9 modularity offers all the functionality we would require, and we assume it will become the standard modularity approach. As such, though we evaluated other options, it did not seem appropriate to adopt a non Java 9 approach at this stage.  

