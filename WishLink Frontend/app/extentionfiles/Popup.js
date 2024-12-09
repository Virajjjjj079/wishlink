document.getElementById('scrape').addEventListener('click', () => {
    chrome.tabs.query({ active: true, currentWindow: true }, (tabs) => {
      chrome.tabs.sendMessage(tabs[0].id, { action: 'scrape' }, (response) => {
        if (response && response.success) {
          document.getElementById('status').innerText = 'Product added to wishlist!';
        } else {
          document.getElementById('status').innerText = 'Failed to add product.';
        }
      });
    });
  });
  