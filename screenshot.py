from playwright.sync_api import sync_playwright
import os

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True, executable_path=r"C:\Users\Admin\AppData\Local\ms-playwright\chromium-1217\chrome-win64\chrome.exe")
    page = browser.new_page(viewport={"width": 1280, "height": 800})

    page.goto('http://localhost:5173/')
    page.wait_for_load_state('networkidle')
    page.wait_for_timeout(2000)
    page.screenshot(path='shop-list.png', full_page=True)
    print("shop-list.png saved")

    page.goto('http://localhost:5173/shops/1')
    page.wait_for_load_state('networkidle')
    page.wait_for_timeout(2000)
    page.screenshot(path='shop-detail.png', full_page=True)
    print("shop-detail.png saved")

    browser.close()
