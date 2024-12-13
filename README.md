# このアプリはなぁに？
Libero Flipにおいて宝の持ち腐れとされるサブディスプレイに無理やりアプリランチャーを表示してアプリを無理やり立ち上げるソフトです。

# どうやって使うの？
起動方法は2つあります。
## 1. アプリランチャーから起動
### 手順
アプリランチャーから起動するとダイアログが出るので「OK」を押します。そのままアプリが終了しますが、既にサブディスプレイ側でランチャーがスタンバイしていますのでそのままサブディスプレイに移動してください。

## 2. 本体を叩いて起動
### 手順
アプリランチャーから起動してダイアログ上で「設定」を押すと作りかけの設定が見えると思います。一番下のユーザー補助の設定画面に飛ぶボタンを押下してください。一番下の「ダウンロードしたアプリ」から「SubDisplayLauncherForLiberoFlip」をタップし、使用するスイッチをオンにしてください。

以降はこの設定がオンになっている場合のみ、サブディスプレイ表示中に上または下から少し強くスマートフォン本体を叩くことでメニューが起動します。
### 注意点
このオプションは「ユーザー補助サービス」を利用して常にデバイスの状態を監視します。この場合はデバイスが叩かれたかどうか(加速度センサーの持続的監視)、現在なんのアプリを起動しているか(使用中のディスプレイがメインディスプレイかサブディスプレイか判別)、画面がついているかどうかを常に監視します。事前準備なくサブディスプレイのみで手順が完結するので非常に手軽ですが、思ったより重たい権限をこのアプリに付与することになるのでご注意ください。

# 外部ディスプレイ起動したアプリ、「ホーム」も「戻る」もできないんだけど
あくまでサブディスプレイなので、その手のジェスチャーはZTEさんは実装していません。まあ当たり前っちゃ当たり前ですね。なので、起動方法2の「ユーザー補助」を使用中の場合のみ、左右から少し強くスマートフォン本体を叩くことで「戻るボタンを押したことにする」ようにしました。

円形ディスプレイ故に画面端のコンテンツはほぼ隠れるので、ナビゲーション用の矢印はほぼ機能しないのでこれがほとんどのアプリで唯一の「戻る」になると思います。

# 安全なの？
**自己責任**でご使用ください。このアプリは**絶賛開発中**です。もし不安ならコードを読んで確認してからのほうが良いでしょう。思った数億倍このアプリは単純にできています。~~そんなアプリを不安定にするな()~~

悪意はないですが、偶然にもとんでもない脆弱性を抱えている可能性は否定できません。あと普通にメモリ管理が下手くそだったりするかもしれません。issueやプルリク、Twitter(新:X)での報告やご意見お待ちしております。

# Libero Flipでしか使えないの？
いやぁ……どうなんでしょうね？少なくとも動作確認はしてないです。

とりあえず仕様上ではサブディスプレイを持つ構成のデバイスであれば動くと思います。なぜならば起動時にディスプレイの枚数が2枚かどうか判別しているだけなので。なのでおそらく他のサブディスプレイ搭載デバイスでも「内部的にただのディスプレイである」場合は使えるんじゃないでしょうか。独自のFWを持った独立デバイスだったら……無理でしょうね。

ちなみに上記の通りなので、普通のスマホにタッチ対応外部ディスプレイを接続した場合でも手順1なら普通に動く気がします。気がするだけです。

---

このソフトウェア及びソースコードにはGNU GPLが適応されています。その中で煮るなり焼くなりお好きにお使いください。
