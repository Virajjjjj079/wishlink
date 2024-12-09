// content.js
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    if (request.action === 'getPageInfo') {
      const pageUrl = window.location.href; // Get the current page URL
      sendResponse({ url: pageUrl });
    }
  });
  console.log('Content script running on the webpage');
  