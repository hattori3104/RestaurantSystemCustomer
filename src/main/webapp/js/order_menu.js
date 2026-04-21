/**
 * 
 */
//タブの切替表示＆カテゴリー絞り込み
document.addEventListener('DOMContentLoaded', () => {

	//全てのラジオボタンを取得
	const tabs = document.querySelectorAll('input[name="tab"]');

	//ラベル要素を取得
	const tabLabels = document.querySelectorAll('.tab-labels label');

	//全ての注文一覧が入っている親要素を取得
	const orderBox = document.querySelector('.tab-contents');
	if (!orderBox) return;

	const orders = orderBox.querySelectorAll('.order_row');

	// ✅ 絞り込み処理：カテゴリに一致する注文だけ表示
	const applyFilter = (selectedCategory) => {
		orders.forEach(order => {
			const category = order.getAttribute('data-category');
			order.style.display = category === selectedCategory ? '' : 'none';
		});

		// ラベルスタイル切り替え
		tabLabels.forEach((label, idx) => {
			label.style.borderBottom = idx === categoryList.indexOf(selectedCategory) ? '2px solid rgb(0,128,0)' : 'none';
			label.style.color = idx === categoryList.indexOf(selectedCategory) ? 'rgb(0,128,0)' : '#999';
		});

		window.scrollTo({ top: 0 });
	};

	//タブ変更時のイベントを登録
	tabs.forEach((tab, index) => {
		tab.addEventListener('change', () => {
			if (tab.checked) {
				const selectedCategory = categoryList[index];
				applyFilter(selectedCategory);
			}
		});
	});

	// 初期表示でチェックされているタブに対応した絞り込みを実行
	const checkedTab = Array.from(tabs).find(tab => tab.checked);
	if (checkedTab) {
		applyFilter(categoryList[Array.from(tabs).indexOf(checkedTab)]);
	}
});

// 商品情報が更新されているかどうかを判定する関数（productInfo配列が直接渡される想定）
function hasDisplayFlagChanged(oldList, newList) {
	// フラグの状態を「IDとflagのペア」でマップ化して比較する
	const getFlagMap = (list) => {
		const map = new Map();
		// listはProductInfoオブジェクトの配列と想定
		list.forEach(info => {
			// 各商品に一意のIDがある前提（なければ調整必要）
			map.set(info.product_id, info.product_display_flag);
		});
		return map;
	};

	const oldMap = getFlagMap(oldList);
	const newMap = getFlagMap(newList);

	// newMapの中身をチェック
	for (const [id, newFlag] of newMap.entries()) {
		const oldFlag = oldMap.get(id);
		// flagの値が変わっていたらtrueを返す
		if (Number(oldFlag) !== Number(newFlag)) {
			return true; // 変化あり
		}
	}
	return false; // 変化なし
}

/*
// ページ読み込み時に前回のリストを復元（なければ空配列）
let previousProductList = JSON.parse(localStorage.getItem('previousProductList') || '[]');

async function fetchProductList() {
	try {
		const response = await fetch(contextPath + '/api/productlist', { cache: "no-store" });
		if (!response.ok) throw new Error('ネットワークエラー');

		const newProductList = await response.json();
		console.log("新しいデータ:", newProductList);
		console.log("以前のデータ:", previousProductList);

		if (hasDisplayFlagChanged(previousProductList, newProductList)) {
			console.log("product_display_flag の変更を検知 → リロード");
			localStorage.setItem('previousProductList', JSON.stringify(newProductList));
			location.reload();
			return;
		} else {
			console.log("変更なし");
		}

		previousProductList = newProductList;
		localStorage.setItem('previousProductList', JSON.stringify(newProductList));

	} catch (err) {
		console.error("商品データ取得に失敗", err);
	}
}
*/

window.addEventListener('load', fetchProductList);
setInterval(fetchProductList, 1000);
