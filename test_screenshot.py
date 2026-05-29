from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    page = browser.new_page(viewport={"width": 1280, "height": 800})

    page.goto('http://localhost:5173/')
    page.wait_for_load_state('networkidle')
    page.screenshot(path='/tmp/shop-list.png', full_page=True)

    page.goto('http://localhost:5173/shops/1')
    page.wait_for_load_state('networkidle')
    page.screenshot(path='/tmp/shop-detail.png', full_page=True)

    browser.close()
    print("Screenshots saved to /tmp/shop-list.png and /tmp/shop-detail.png")
