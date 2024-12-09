// background.js
// background.js
// background.js
chrome.action.onClicked.addListener((tab) => {
    chrome.storage.local.get(['userId', 'isAuthenticated'], (result) => {
      if (result.isAuthenticated && result.userId) {
        chrome.tabs.sendMessage(tab.id, { action: 'getPageInfo' }, (response) => {
          if (response && response.url) {
            const apiUrl = `http://192.168.0.113:8080/scrape/${result.userId}`;
            fetch(apiUrl, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(response.url)
            })
            .then(response => {
              if (!response.ok) {
                throw new Error(`Server responded with ${response.status}`);
              }
              return response.json();
            })
            .then(data => {
              console.log('Product details saved:', data);
              alert('Product successfully added to wishlist!');
            })
            .catch(error => {
              console.error('Error:', error);
              alert('Failed to add product.'); // Update this alert to match your issue.
            });
          } else {
            console.error('Failed to retrieve URL from content script.');
          }
        });
      } else {
        console.error('User not authenticated or userId not found in localStorage');
      }
      chrome.runtime.onInstalled.addListener(() => {
        console.log('Background script running');
      });
      
    });
  });
  