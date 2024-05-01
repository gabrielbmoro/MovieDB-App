//
//  iosAppApp.swift
//  iosApp
//
//  Created by Gabriel Bronzatti Moro on 01/05/24.
//

import SwiftUI
import ComposeApp

@main
struct iosAppApp: App {

    init() {
        KoinHelperKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
