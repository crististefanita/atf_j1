package atf.shop.tests.support.business;

import com.microsoft.playwright.Page;

/** Controlled display corruption; it never dispatches an application request or changes configured expected values. */
public final class ShopBusinessFault {
    public enum Point { ACCOUNT, CART, PURCHASE }
    public static final String MARKER = "atf-business-fault";
    private final Page page;
    private final Point point;

    public ShopBusinessFault(Page page, Point point) { this.page = page; this.point = point; }

    public void arm() {
        String script = "(" + SCRIPT + ")(" + "'" + point.name() + "'" + ")";
        page.addInitScript(script);
        page.evaluate(script);
    }

    public int injections() {
        if (page.isClosed() || !page.url().startsWith("http")) return 0;
        return ((Number) page.evaluate("() => Number(sessionStorage.getItem('atf-business-fault-count') || 0)")).intValue();
    }

    private static final String SCRIPT = """
            point => {
                if (window.__atfBusinessFaultObserver) return;
                const apply = () => {
                    if (sessionStorage.getItem('atf-business-fault-count')) return;
                    let target;
                    if (point === 'ACCOUNT') {
                        if (!location.pathname.endsWith('/account')) return;
                        target = document.querySelector('h1');
                        if (!target) return;
                        target.style.display = 'none';
                    } else if (point === 'CART') {
                        target = document.querySelector('[data-test="line-price"]');
                        if (!target || !target.textContent.trim()) return;
                        target.textContent = '0.01';
                    } else {
                        if (!document.body?.textContent.includes('Thanks for your order!')) return;
                        const walker = document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT);
                        let node;
                        while (node = walker.nextNode()) {
                            if (/INV-[A-Za-z0-9_-]+/.test(node.nodeValue)) { target = node; break; }
                        }
                        if (!target) return;
                        target.nodeValue = target.nodeValue.replace(/INV-[A-Za-z0-9_-]+/g, 'ATF-REJECTED');
                    }
                    sessionStorage.setItem('atf-business-fault-count', '1');
                    const badge = document.createElement('div'); badge.id = 'atf-business-fault';
                    badge.textContent = 'CONTROLLED BUSINESS FAILURE: ' + point;
                    badge.style.cssText = 'position:fixed;left:16px;top:100px;width:300px;height:44px;'
                        + 'background:#ff00ff;color:#000;font:12px sans-serif;z-index:2147483647;';
                    document.body.appendChild(badge);
                };
                window.__atfBusinessFaultObserver = new MutationObserver(apply);
                window.__atfBusinessFaultObserver.observe(document, {childList:true,subtree:true});
                apply();
            }
            """;
}
