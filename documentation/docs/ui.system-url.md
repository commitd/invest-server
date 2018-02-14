---
id: ui.system-url
title: "System design: URL"
date: "2017-10-20"
order: 2180
hide: false
draft: false
---

URLs are important for web applications. When they are displayed they provide a visual cue to the user as to their location.

Even if they are not displayed they serve the role as bookmarks, sharing links to content, and to enable refresh in the browser. In these cases the user has an expectation that when the page loads (from bookmark, refresh or linking a shared link) it will display the same information as when the link was taken.

The definition of same information here is somewhat complex:

* If we are reading a specific article we would expect it to show the same artficle, but should it retain the same position on the page (eg how far the user has scrolled)
* If the user is half way through completing a form online, we would expect the same form to be displayed, but should partially complete information be displayed too?

Within Invest we use the Intent system to define page links. That is the URL encodes the plugin being displayed, the action and payload it was opened with. This leave it open to the plugin developer as to the level of granularity they should saved on the URL.

Ignoring authentication flows, when the user refreshs or opens a link, the Invest framework behaves in the same manner is would if it has nvaigated to that page via an click from another plugin.

## History

Management of location within the browser, ie the URL, is handled by the HIstory API. There are many implementations of this avaialble in React Router, though the History package.

The 'neatest' solution is the Browser History which provides clean urls, eg http://server/view/plugins1. However we are using the Hash History, http://server/#/view/plugins1. Browser History requires some setup son the Server side which adds complexity for proxying and other options. Therefore we use Hash History which functions without any deployment configuration.

## Recommendations

We recommend that a coarse granularity is retained on the URL. For example, for a plugin that allows customer information to be editted then record the customer id, but not any edits made. In the case of a document search, record the search query and perhaps the page of results being displayed, but the scroll position in the browser. 

The reason for this is simply that the notion of intent has a clear business/user meaning, where as some of the other details are very implementation specific and it is not clear the user wanted that behaviour. From a developer perspective accessing and resetting aspects like scroll offset are very complex - not least of which because the screen size of one user may be different to the screen size of the user who is linked the shared link.

